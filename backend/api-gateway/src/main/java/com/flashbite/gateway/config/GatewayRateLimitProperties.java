package com.flashbite.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "flashbite.gateway.rate-limit")
public class GatewayRateLimitProperties {
    private int publicIpRequestsPerWindow = 60;
    private int protectedIpRequestsPerWindow = 120;
    private int protectedUserRequestsPerWindow = 60;
    private int windowSeconds = 60;

    public int getPublicIpRequestsPerWindow() {
        return publicIpRequestsPerWindow;
    }

    public void setPublicIpRequestsPerWindow(int publicIpRequestsPerWindow) {
        this.publicIpRequestsPerWindow = publicIpRequestsPerWindow;
    }

    public int getProtectedIpRequestsPerWindow() {
        return protectedIpRequestsPerWindow;
    }

    public void setProtectedIpRequestsPerWindow(int protectedIpRequestsPerWindow) {
        this.protectedIpRequestsPerWindow = protectedIpRequestsPerWindow;
    }

    public int getProtectedUserRequestsPerWindow() {
        return protectedUserRequestsPerWindow;
    }

    public void setProtectedUserRequestsPerWindow(int protectedUserRequestsPerWindow) {
        this.protectedUserRequestsPerWindow = protectedUserRequestsPerWindow;
    }

    public int getWindowSeconds() {
        return windowSeconds;
    }

    public void setWindowSeconds(int windowSeconds) {
        this.windowSeconds = windowSeconds;
    }
}
