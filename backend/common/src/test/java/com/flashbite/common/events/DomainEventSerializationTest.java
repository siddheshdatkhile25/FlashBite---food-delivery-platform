package com.flashbite.common.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flashbite.common.events.payload.OrderPlacedPayload;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventSerializationTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void serializesAndDeserializesDomainEvent() throws Exception {
        UUID orderId = UUID.randomUUID();
        var payload = new OrderPlacedPayload(
                orderId,
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("19.99")
        );

        var event = DomainEvent.of(orderId.toString(), 1, EventTopics.ORDER_PLACED, payload);

        String json = mapper.writeValueAsString(event);
        DomainEvent<?> roundTrip = mapper.readValue(json, DomainEvent.class);

        assertThat(roundTrip.aggregateId()).isEqualTo(orderId.toString());
        assertThat(roundTrip.type()).isEqualTo(EventTopics.ORDER_PLACED);
        assertThat(roundTrip.version()).isEqualTo(1);
    }
}
