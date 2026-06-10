package com.flashbite.common.events;

public final class EventTopics {
    public static final String ORDER_PLACED = "order.placed";
    public static final String PAYMENT_SUCCESS = "payment.success";
    public static final String PAYMENT_FAILED = "payment.failed";
    public static final String ORDER_CONFIRMED = "order.confirmed";
    public static final String ORDER_READY = "order.ready";
    public static final String DRIVER_ASSIGNED = "driver.assigned";
    public static final String ORDER_PICKED_UP = "order.picked_up";
    public static final String ORDER_IN_TRANSIT = "order.in_transit";
    public static final String DRIVER_LOCATION_UPDATED = "driver.location.updated";
    public static final String ORDER_DELIVERED = "order.delivered";
    public static final String ORDER_CANCELLED = "order.cancelled";
    public static final String REVIEW_SUBMITTED = "review.submitted";

    private EventTopics() {
            
    }

}
