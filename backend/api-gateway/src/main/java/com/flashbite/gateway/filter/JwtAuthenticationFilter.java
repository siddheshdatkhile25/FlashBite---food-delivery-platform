package com.flashbite.gateway.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.flashbite.common.api.ApiConstants;
import com.flashbite.common.api.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashbite.common.exception.ErrorCode;
import com.flashbite.common.exception.FlashBiteException;
import com.flashbite.gateway.config.GatewayRateLimitProperties;
import com.flashbite.gateway.config.GatewaySecurityProperties;
import com.flashbite.gateway.ratelimit.InMemorySlidingWindowRateLimiter;
import com.flashbite.gateway.ratelimit.InMemorySlidingWindowRateLimiter.RateLimitDecision;
import com.flashbite.gateway.security.JwtAuthenticationService;
import com.flashbite.gateway.security.JwtAuthenticationService.AuthenticatedUser;


import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final ObjectMapper objectMapper;
    private final GatewaySecurityProperties securityProperties;
    private final GatewayRateLimitProperties rateLimitProperties;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final InMemorySlidingWindowRateLimiter rateLimiter;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(
            ObjectMapper objectMapper,
            GatewaySecurityProperties securityProperties,
            GatewayRateLimitProperties rateLimitProperties,
            JwtAuthenticationService jwtAuthenticationService,
            InMemorySlidingWindowRateLimiter rateLimiter
    ) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
        this.rateLimitProperties = rateLimitProperties;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String traceId = resolveTraceId(request.getHeaders());

        // Add trace ID to request and response headers for tracing
        ServerHttpRequest tracedRequest = request.mutate()
                .headers(headers -> headers.set(ApiConstants.TRACE_ID_HEADER, traceId))
                .build();
        ServerWebExchange tracedExchange = exchange.mutate().request(tracedRequest).build();
        tracedExchange.getResponse().getHeaders().set(ApiConstants.TRACE_ID_HEADER, traceId);

        long startedAtNanos = System.nanoTime();
        String clientIp = clientIp(tracedRequest);
        boolean isPublicPath = isPublicPath(path);
        Duration window = Duration.ofSeconds(rateLimitProperties.getWindowSeconds());

        // Rate limit based on client IP
        RateLimitDecision ipDecision = rateLimiter.evaluate(
                "ip:" + clientIp + ":" + (isPublicPath ? "public" : "protected"),
                isPublicPath
                        ? rateLimitProperties.getPublicIpRequestsPerWindow()
                        : rateLimitProperties.getProtectedIpRequestsPerWindow(),
                window
        );
        if (!ipDecision.allowed()) {
            // If the client IP has exceeded its rate limit, return a 429 Too Many Requests response
            return writeRateLimitResponse(tracedExchange, traceId, ipDecision.retryAfter());
        }

        // If the path is public, we don't need to authenticate the user
        // If the path is protected, we need to authenticate the user and rate limit based on

        ServerWebExchange authenticatedExchange = tracedExchange;
        boolean authenticated = false;
        if (!isPublicPath) {
            try {
                // Authenticate the user using the JWT token from the Authorization header
                AuthenticatedUser user = jwtAuthenticationService.authenticate(
                        tracedRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
                );
                authenticated = true;
                // Rate limit based on user ID
                RateLimitDecision userDecision = rateLimiter.evaluate(
                        "user:" + user.userId(),
                        rateLimitProperties.getProtectedUserRequestsPerWindow(),
                        window
                );
                if (!userDecision.allowed()) {
                    // If the user has exceeded their rate limit, return a 429 Too Many Requests response
                    return writeRateLimitResponse(tracedExchange, traceId, userDecision.retryAfter());
                }

                // Add user ID and role to request headers for downstream services
                ServerHttpRequest authenticatedRequest = tracedRequest.mutate()
                        .headers(headers -> {
                            headers.set(securityProperties.getUserIdHeader(), user.userId());
                            if (StringUtils.hasText(user.role())) {
                                
                                headers.set(securityProperties.getUserRoleHeader(), user.role());
                            }
                        })
                        .build();
                // Update the exchange with the authenticated request
                authenticatedExchange = tracedExchange.mutate().request(authenticatedRequest).build();
            } catch (FlashBiteException exception) {
                return writeErrorResponse(
                        tracedExchange.getResponse(),
                        traceId,
                        exception.getStatus(),
                        exception.getErrorCode(),
                        exception.getMessage()
                );
            }
        }

        // Log the request details, including trace ID, method, path, client IP, and authentication status
        final boolean requestAuthenticated = authenticated;
        log.info(
                "Gateway request traceId={} method={} path={} clientIp={} authenticated={}",
                traceId,
                request.getMethod(),
                path,
                clientIp,
                requestAuthenticated
        );

        // Continue the filter chain with the authenticated exchange
        ServerWebExchange downstreamExchange = authenticatedExchange;
        return chain.filter(downstreamExchange)
                // Log the response details, including trace ID, method, path, status code, and duration
                .doFinally(signalType -> log.info(
                        "Gateway response traceId={} method={} path={} status={} durationMs={}",
                        traceId,
                        request.getMethod(),
                        path,
                        downstreamExchange.getResponse().getStatusCode(),
                        Duration.ofNanos(System.nanoTime() - startedAtNanos).toMillis()
                ));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private boolean isPublicPath(String path) {
        List<String> publicPaths = securityProperties.getPublicPaths();
        for (String publicPath : publicPaths) {
            if (pathMatcher.match(publicPath, path)) {
                return true;
            }
        }
        return false;
    }

    // Resolve the trace ID from the request headers or generate a new one if not present
    private String resolveTraceId(HttpHeaders headers) {
        String traceId = headers.getFirst(ApiConstants.TRACE_ID_HEADER);
        return StringUtils.hasText(traceId) ? traceId : UUID.randomUUID().toString();
    }

    private String clientIp(ServerHttpRequest request) {
        String forwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
        if (StringUtils.hasText(forwardedFor)) {
            return forwardedFor.split(",")[0].trim();
        }

        if (request.getRemoteAddress() != null && request.getRemoteAddress().getAddress() != null) {
            return request.getRemoteAddress().getAddress().getHostAddress();
        }

        return "unknown";
    }

    // Write a 429 Too Many Requests response with the appropriate headers and body
    private Mono<Void> writeRateLimitResponse(ServerWebExchange exchange, String traceId, Duration retryAfter) {
        long retryAfterSeconds = Math.max(1L, (long) Math.ceil(retryAfter.toMillis() / 1000.0));
        exchange.getResponse().getHeaders().set(HttpHeaders.RETRY_AFTER, String.valueOf(retryAfterSeconds));
        return writeErrorResponse(
                exchange.getResponse(),
                traceId,
                HttpStatus.TOO_MANY_REQUESTS,
                ErrorCode.TOO_MANY_REQUESTS,
                "Rate limit exceeded"
        );
    }

    // Write an error response with the given status, error code, and message
    private Mono<Void> writeErrorResponse(
            ServerHttpResponse response,
            String traceId,
            HttpStatus status,
            ErrorCode errorCode,
            String message
    ) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().set(ApiConstants.TRACE_ID_HEADER, traceId);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode.code(), message, traceId);
        try {
            byte[] body = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(body);
            return response.writeWith(Mono.just(buffer));
        } catch (IOException exception) {
            byte[] fallback = ("{\"code\":\"" + errorCode.code() + "\",\"message\":\"" + message + "\"}")
                    .getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(fallback);
            return response.writeWith(Mono.just(buffer));
        }
    }
}
