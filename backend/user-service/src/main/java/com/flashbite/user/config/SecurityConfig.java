package com.flashbite.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import com.flashbite.user.security.jwt.JwtAuthenticationConverter;
import com.flashbite.user.security.oauth2.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AuthSecurityProperties authSecurityProperties;

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(
                authSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Disable CSRF because this service sits behind the API Gateway and usually relies on JWTs
            .csrf(csrf -> csrf.disable())
            
            // Allow all requests to pass through initially. 
            // The API Gateway handles general JWT auth blocking, but we must let the oauth2 routes 
            // trigger the Spring Security OAuth2 filters in this local container.
            
            // Step 3 Configuration: Override default OAuth2 login paths
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(auth -> auth
                    .baseUri("/api/v1/auth/oauth2")
                )
                .redirectionEndpoint(redirect -> redirect
                    .baseUri("/api/v1/auth/oauth2/callback/*")
                )
                .successHandler(oAuth2SuccessHandler)
            )
            // Add the Resource Server config utilizing your new converter
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter()))
            )

            // Authorize Http Requests
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/api/v1/health").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .requestMatchers("/api/v1/users/**").hasAnyRole("CUSTOMER", "ADMIN", "RESTAURANT", "DRIVER")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/restaurant/**").hasAnyRole("RESTAURANT", "ADMIN")
                .requestMatchers("/api/v1/driver/**").hasAnyRole("DRIVER", "ADMIN")
                .requestMatchers("/api/v1/cart/**" , "/api/v1/orders/**").hasAnyRole("CUSTOMER", "ADMIN")
                .anyRequest().authenticated()
            )
            
            
            // Keep it stateless as fits a microservice, though OAuth2 flow briefly uses sessions
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }
}
