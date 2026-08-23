package com.flashbite.gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        GatewayRateLimitProperties.class,
        GatewayRouteProperties.class,
        GatewaySecurityProperties.class
})
public class GatewayRouteConfig {

    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder, GatewayRouteProperties routes) {
        return builder.routes()

                .route("user-service-auth", r -> r
                        .path("/api/v1/auth/**", "/api/v1/users/**", "/api/v1/health" , "/")
                        .uri(routes.getUserServiceUri()))
                .route("restaurant-service-public-details", r -> r
                        .path("/api/v1/restaurants/*/menu", "/api/v1/restaurants/*/reviews/**")
                        .uri(routes.getRestaurantServiceUri()))
                .route("restaurant-service", r -> r
                        .path("/api/v1/restaurant/**")
                        .uri(routes.getRestaurantServiceUri()))
                .route("restaurant-search-service", r -> r
                        .path("/api/v1/restaurants/**", "/api/v1/search/**")
                        .uri(routes.getSearchServiceUri()))
                .route("order-service", r -> r
                        .path("/api/v1/cart/**", "/api/v1/orders/**")
                        .uri(routes.getOrderServiceUri()))
                .route("payment-service-admin", r -> r
                        .path("/api/v1/admin/refunds/**")
                        .uri(routes.getPaymentServiceUri()))
                .route("payment-service-restaurant", r -> r
                        .path("/api/v1/restaurant/payouts/**")
                        .uri(routes.getPaymentServiceUri()))
                .route("payment-service", r -> r
                        .path("/api/v1/payments/**")
                        .uri(routes.getPaymentServiceUri()))
                .route("delivery-service", r -> r
                        .path("/api/v1/driver/**")
                        .uri(routes.getDeliveryServiceUri()))
                .route("notification-service", r -> r
                        .path("/api/v1/notifications/**")
                        .uri(routes.getNotificationServiceUri()))
                .route("admin-user-service", r -> r
                        .path("/api/v1/admin/users/**", "/api/v1/admin/restaurants/**")
                        .uri(routes.getUserServiceUri()))
                .route("analytics-admin-orders", r -> r
                        .path("/api/v1/admin/orders/**")
                        .uri(routes.getAnalyticsServiceUri()))
                .route("analytics-service", r -> r
                        .path("/api/v1/admin/**")
                        .uri(routes.getAnalyticsServiceUri()))
                // OpenAPI docs routes
                .route("user-service-docs", r -> r.path("/v3/api-docs/user-service").filters(f -> f.rewritePath("/v3/api-docs/user-service(/?.*)", "/v3/api-docs$1")).uri(routes.getUserServiceUri()))
                .route("restaurant-service-docs", r -> r.path("/v3/api-docs/restaurant-service").filters(f -> f.rewritePath("/v3/api-docs/restaurant-service(/?.*)", "/v3/api-docs$1")).uri(routes.getRestaurantServiceUri()))
                .route("order-service-docs", r -> r.path("/v3/api-docs/order-service").filters(f -> f.rewritePath("/v3/api-docs/order-service(/?.*)", "/v3/api-docs$1")).uri(routes.getOrderServiceUri()))
                .route("payment-service-docs", r -> r.path("/v3/api-docs/payment-service").filters(f -> f.rewritePath("/v3/api-docs/payment-service(/?.*)", "/v3/api-docs$1")).uri(routes.getPaymentServiceUri()))
                .route("delivery-service-docs", r -> r.path("/v3/api-docs/delivery-service").filters(f -> f.rewritePath("/v3/api-docs/delivery-service(/?.*)", "/v3/api-docs$1")).uri(routes.getDeliveryServiceUri()))
                .route("notification-service-docs", r -> r.path("/v3/api-docs/notification-service").filters(f -> f.rewritePath("/v3/api-docs/notification-service(/?.*)", "/v3/api-docs$1")).uri(routes.getNotificationServiceUri()))
                .route("search-service-docs", r -> r.path("/v3/api-docs/search-service").filters(f -> f.rewritePath("/v3/api-docs/search-service(/?.*)", "/v3/api-docs$1")).uri(routes.getSearchServiceUri()))
                .route("analytics-service-docs", r -> r.path("/v3/api-docs/analytics-service").filters(f -> f.rewritePath("/v3/api-docs/analytics-service(/?.*)", "/v3/api-docs$1")).uri(routes.getAnalyticsServiceUri()))
                .build();
    }
}
