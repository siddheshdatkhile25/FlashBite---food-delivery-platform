package com.flashbite.common.domain;

public enum OrderStatus {
    PLACED,
    CONFIRMED,
    PREPARING,
    READY,
    PICKED_UP,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case PLACED -> next == CONFIRMED || next == CANCELLED;
            case CONFIRMED -> next == PREPARING || next == CANCELLED;
            case PREPARING -> next == READY;
            case READY -> next == PICKED_UP;
            case PICKED_UP -> next == DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
