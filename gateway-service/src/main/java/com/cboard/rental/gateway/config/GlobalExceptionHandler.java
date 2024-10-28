package com.cboard.rental.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import io.jsonwebtoken.JwtException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        log.error("JWT error: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Invalid or expired JWT token",
            "JWT Error",
            "ERR_JWT",
            null,
            null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    
    
    

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            "Resource Not Found",
            "ERR404",
            ex.getResourceName(),
            ex.getResourceId()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Validation failed: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Method argument validation failed: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage() + " (rejected value: " + error.getRejectedValue() + ")");
        }
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /*@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String rootCauseMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Unknown error";
        log.error("Data integrity violation: {}", rootCauseMessage, ex);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Data integrity violation: " + rootCauseMessage,
            "Data Integrity Violation",
            "ERR001",
            null,
            null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            "Unexpected Error",
            "ERR500",
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // ErrorResponse class with a timestamp and error code
    public static class ErrorResponse {
        private int status;
        private String message;
        private String errorType;
        private String errorCode;
        private String resource;
        private Object resourceId;
        private LocalDateTime timestamp;

        public ErrorResponse(int status, String message, String errorType, String errorCode, String resource, Object resourceId) {
            this.status = status;
            this.message = message + " - Error occurred";
            this.errorType = errorType;
            this.errorCode = errorCode;
            this.resource = resource;
            this.resourceId = resourceId;
            this.timestamp = LocalDateTime.now();
        }

        // Getters and setters
        // (Getters and Setters omitted for brevity)
    }

    // ValidationErrorResponse class with validation errors
    public static class ValidationErrorResponse extends ErrorResponse {
        private Map<String, String> validationErrors;

        public ValidationErrorResponse(int status, String message, Map<String, String> validationErrors) {
            super(status, message, "Validation Error", "ERR400", null, null);
            this.validationErrors = validationErrors;
        }

        public Map<String, String> getValidationErrors() {
            return validationErrors;
        }

        public void setValidationErrors(Map<String, String> validationErrors) {
            this.validationErrors = validationErrors;
        }
    }
}
