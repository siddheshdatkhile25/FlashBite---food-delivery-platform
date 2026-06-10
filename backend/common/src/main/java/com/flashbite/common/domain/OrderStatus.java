package com.flashbite.common.domain;

public enum OrderStatus {
    PLACED,
    CONFIRMED,
    PREPARING,
    READY,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus status) {
        return switch (this) {
            case PLACED -> status == CONFIRMED || status == CANCELLED;
            case CONFIRMED -> status == PREPARING || status == CANCELLED;
            case PREPARING -> status == READY ;
            case READY -> status == PICKED_UP ;
            case PICKED_UP -> status == IN_TRANSIT ;
            case IN_TRANSIT -> status == DELIVERED ;
            case DELIVERED -> false;
            case CANCELLED -> false;
        };
    }
}
