package com.flashbite.common.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @AfterEach
    void cleanup() {
        MDC.clear();
    }

    @Test
    void mapsFlashBiteExceptionToErrorResponse() {
        MDC.put("traceId", "trace-123");

        var ex = new FlashBiteException(
                ErrorCode.CONFLICT,
                HttpStatus.CONFLICT,
                "Duplicate email"
        );

        var response = handler.handleFlashBite(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("CONFLICT");
        assertThat(response.getBody().message()).isEqualTo("Duplicate email");
        assertThat(response.getBody().traceId()).isEqualTo("trace-123");
    }
}
