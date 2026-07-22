package com.flashbite.gateway;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayApplicationTest {
    private static final String TEST_SECRET = "flashbite-gateway-test-secret-1234567890abcdef";
    private static final AtomicReference<String> lastPath = new AtomicReference<>();
    private static final AtomicReference<String> lastTraceId = new AtomicReference<>();
    private static final AtomicReference<String> lastUserId = new AtomicReference<>();
    private static final AtomicInteger hitCount = new AtomicInteger();
    private static final DisposableServer DOWNSTREAM_SERVER = HttpServer.create()
            .port(0)
            .route(routes -> routes.route(
                    request -> true,
                    (request, response) -> {
                        hitCount.incrementAndGet();
                        lastPath.set(request.uri());
                        lastTraceId.set(request.requestHeaders().get("X-Trace-Id"));
                        lastUserId.set(request.requestHeaders().get("X-User-Id"));
                        return response.status(200)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .sendString(Mono.just("{\"path\":\"" + request.uri() + "\"}"));
                    }
            ))
            .bindNow();

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void gatewayProperties(DynamicPropertyRegistry registry) {
        String downstreamUri = "http://localhost:" + DOWNSTREAM_SERVER.port();
        registry.add("eureka.client.enabled", () -> "false");
        registry.add("flashbite.gateway.security.jwt-secret", () -> TEST_SECRET);
        registry.add("flashbite.gateway.rate-limit.public-ip-requests-per-window", () -> "100");
        registry.add("flashbite.gateway.rate-limit.protected-ip-requests-per-window", () -> "100");
        registry.add("flashbite.gateway.rate-limit.protected-user-requests-per-window", () -> "2");
        registry.add("flashbite.gateway.rate-limit.window-seconds", () -> "60");
        registry.add("flashbite.gateway.routes.user-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.restaurant-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.order-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.payment-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.delivery-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.notification-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.search-service-uri", () -> downstreamUri);
        registry.add("flashbite.gateway.routes.analytics-service-uri", () -> downstreamUri);
    }

    @BeforeEach
    void resetCapture() {
        lastPath.set(null);
        lastTraceId.set(null);
        lastUserId.set(null);
        hitCount.set(0);
    }

    @AfterAll
    static void stopDownstream() {
        DOWNSTREAM_SERVER.disposeNow();
    }

    @Test
    void authRoutesRemainPublic() {
        webTestClient.post()
                .uri("/api/v1/auth/login")
                .exchange()
                .expectStatus().isOk();

        assertThat(lastPath.get()).isEqualTo("/api/v1/auth/login");
        assertThat(hitCount.get()).isEqualTo(1);
    }

    @Test
    void protectedRoutesRejectMissingJwt() throws Exception {
        JsonNode body = webTestClient.get()
                .uri("/api/v1/orders/123")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().exists("X-Trace-Id")
                .expectBody(JsonNode.class)
                .returnResult()
                .getResponseBody();

        assertThat(body).isNotNull();
        assertThat(body.get("code").asText()).isEqualTo("UNAUTHORIZED");
        assertThat(hitCount.get()).isZero();
    }

    @Test
    void validJwtIsForwardedWithTraceId() {
        String token = jwtFor("customer-123", "CUSTOMER");

        webTestClient.get()
                .uri("/api/v1/orders/123")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("X-Trace-Id", "trace-abc")
                .exchange()
                .expectStatus().isOk();

        assertThat(lastPath.get()).isEqualTo("/api/v1/orders/123");
        assertThat(lastTraceId.get()).isEqualTo("trace-abc");
        assertThat(lastUserId.get()).isEqualTo("customer-123");
    }

    @Test
    void rateLimitReturnsTooManyRequestsWithRetryAfter() throws Exception {
        String token = jwtFor("rate-limited-user", "CUSTOMER");

        webTestClient.get()
                .uri("/api/v1/orders/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/api/v1/orders/2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();

        JsonNode body = webTestClient.get()
                .uri("/api/v1/orders/3")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isEqualTo(429)
                .expectHeader().exists(HttpHeaders.RETRY_AFTER)
                .expectBody(JsonNode.class)
                .returnResult()
                .getResponseBody();

        assertThat(body).isNotNull();
        assertThat(body.get("code").asText()).isEqualTo("TOO_MANY_REQUESTS");
        assertThat(hitCount.get()).isEqualTo(2);
    }

    @Test
    void actuatorHealthIsExposed() {
        webTestClient.get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }

    private String jwtFor(String subject, String role) {
        SecretKeySpec secretKey = new SecretKeySpec(TEST_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(new com.nimbusds.jose.jwk.source.ImmutableSecret<>(secretKey));
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(600))
                .claim("role", role)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        )).getTokenValue();
    }
}
