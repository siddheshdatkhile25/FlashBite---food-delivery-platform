package com.flashbite.common.exception;

import com.flashbite.common.api.ErrorResponse;
import com.flashbite.common.web.MdcKeys;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlashBiteException.class)
    public ResponseEntity<ErrorResponse> handleFlashBite(FlashBiteException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.of(
                        ex.getErrorCode().code(),
                        ex.getMessage(),
                        traceId(),
                        ex.getDetails()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() == null
                                ? "invalid"
                                : fieldError.getDefaultMessage(),
                        (first, second) -> first
                ));

        return ResponseEntity.badRequest().body(ErrorResponse.of(
                ErrorCode.VALIDATION_ERROR.code(),
                "Request validation failed",
                traceId(),
                details
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(
                ErrorCode.VALIDATION_ERROR.code(),
                ex.getMessage(),
                traceId()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(
                ErrorCode.INTERNAL_ERROR.code(),
                "An unexpected error occurred",
                traceId()
        ));
    }

    private static String traceId() {
        return MDC.get(MdcKeys.TRACE_ID);
    }
}
