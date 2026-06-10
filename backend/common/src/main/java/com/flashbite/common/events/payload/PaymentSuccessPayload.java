package com.flashbite.common.events.payload;

import java.util.UUID;

public record PaymentSuccessPayload(
    UUID orderId,
    UUID transactionId
) {

}
