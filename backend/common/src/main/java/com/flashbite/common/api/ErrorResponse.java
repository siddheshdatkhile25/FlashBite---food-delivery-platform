package com.flashbite.common.api;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String code,
    String message,
    String traceId,
    Instant timestamp,
    Map<String, Object> details
) {
    public static ErrorResponse of(String code, String message, String traceId) {
        return new ErrorResponse(code, message, traceId, Instant.now(), null);
    }
    public static ErrorResponse of(String code, String message, String traceId, Map<String, Object> details) {
        return new ErrorResponse(code, message, traceId, Instant.now(), details);
    }
}


   
    

    

