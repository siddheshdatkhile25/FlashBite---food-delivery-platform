package com.flashbite.common.events;

import java.time.Instant;
import java.util.UUID;

public record DomainEvent<T>(
    UUID eventId,
    Instant timestamp,
    String aggregateId,
    int version,
    String type,
    T payload
){

    public static <T> DomainEvent<T> of(String aggregateId, int version, String type, T payload) {
        return new DomainEvent<>(
            UUID.randomUUID(), 
            Instant.now(), 
            aggregateId, 
            version, 
            type, 
            payload
        );
    
    }

}
