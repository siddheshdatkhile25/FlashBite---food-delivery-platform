package com.flashbite.common.events.payload;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderPlacedPayload(
    UUID orderId,
    UUID customerId,
    UUID restaurantId,
    BigDecimal totalAmount

) {

}
