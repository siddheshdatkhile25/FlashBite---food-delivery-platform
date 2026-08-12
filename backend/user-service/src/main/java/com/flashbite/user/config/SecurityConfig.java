package com.flashbite.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Disable CSRF because this service sits behind the API Gateway and usually relies on JWTs
            .csrf(csrf -> csrf.disable())
            
            // Allow all requests to pass through initially. 
            // The API Gateway handles general JWT auth blocking, but we must let the oauth2 routes 
            // trigger the Spring Security OAuth2 filters in this local container.
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().permitAll() 
            )
            
            // Step 3 Configuration: Override default OAuth2 login paths
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(auth -> auth
                    .baseUri("/api/v1/auth/oauth2")
                )
                .redirectionEndpoint(redirect -> redirect
                    .baseUri("/api/v1/auth/oauth2/callback/*")
                )
            )
            
            // Keep it stateless as fits a microservice, though OAuth2 flow briefly uses sessions
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }
}
