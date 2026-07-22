package com.flashbite.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "flashbite.gateway.routes")
public class GatewayRouteProperties {
    private String userServiceUri = "http://localhost:8082";
    private String restaurantServiceUri = "http://localhost:8083";
    private String orderServiceUri = "http://localhost:8084";
    private String paymentServiceUri = "http://localhost:8085";
    private String deliveryServiceUri = "http://localhost:8086";
    private String notificationServiceUri = "http://localhost:8087";
    private String searchServiceUri = "http://localhost:8088";
    private String analyticsServiceUri = "http://localhost:8089";

    public String getUserServiceUri() {
        return userServiceUri;
    }

    public void setUserServiceUri(String userServiceUri) {
        this.userServiceUri = userServiceUri;
    }

    public String getRestaurantServiceUri() {
        return restaurantServiceUri;
    }

    public void setRestaurantServiceUri(String restaurantServiceUri) {
        this.restaurantServiceUri = restaurantServiceUri;
    }

    public String getOrderServiceUri() {
        return orderServiceUri;
    }

    public void setOrderServiceUri(String orderServiceUri) {
        this.orderServiceUri = orderServiceUri;
    }

    public String getPaymentServiceUri() {
        return paymentServiceUri;
    }

    public void setPaymentServiceUri(String paymentServiceUri) {
        this.paymentServiceUri = paymentServiceUri;
    }

    public String getDeliveryServiceUri() {
        return deliveryServiceUri;
    }

    public void setDeliveryServiceUri(String deliveryServiceUri) {
        this.deliveryServiceUri = deliveryServiceUri;
    }

    public String getNotificationServiceUri() {
        return notificationServiceUri;
    }

    public void setNotificationServiceUri(String notificationServiceUri) {
        this.notificationServiceUri = notificationServiceUri;
    }

    public String getSearchServiceUri() {
        return searchServiceUri;
    }

    public void setSearchServiceUri(String searchServiceUri) {
        this.searchServiceUri = searchServiceUri;
    }

    public String getAnalyticsServiceUri() {
        return analyticsServiceUri;
    }

    public void setAnalyticsServiceUri(String analyticsServiceUri) {
        this.analyticsServiceUri = analyticsServiceUri;
    }
}
