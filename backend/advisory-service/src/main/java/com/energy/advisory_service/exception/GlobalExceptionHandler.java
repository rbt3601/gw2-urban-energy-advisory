package com.energy.advisory_service.exception;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        return buildError(ex.getStatus(), ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            String key = switch (error.getCode()) {
                case "AssertTrue" -> mapAssertTrueField(error.getDefaultMessage(), error.getObjectName());
                default -> error.getObjectName();
            };
            errors.putIfAbsent(key, error.getDefaultMessage());
        });

        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        v -> v.getMessage(),
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            String fieldPath = buildJsonPath(invalidFormatException.getPath());
            String targetType = invalidFormatException.getTargetType() != null
                    ? invalidFormatException.getTargetType().getSimpleName()
                    : "value";

            Map<String, String> errors = new LinkedHashMap<>();
            Object badValue = invalidFormatException.getValue();
            errors.put(fieldPath.isBlank() ? "requestBody" : fieldPath,
                    "Invalid value '" + String.valueOf(badValue) + "' for " + targetType);

            return buildError(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), errors);
        }

        Map<String, String> errors = new LinkedHashMap<>();
        String rawMessage = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();

        if (rawMessage != null && rawMessage.contains("Required request body is missing")) {
            errors.put("requestBody", "Request body is required");
            return buildError(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), errors);
        }

        errors.put("requestBody", "Request body is missing, empty, or malformed");
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI(), null);
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message, String path, Map<String, String> validationErrors) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(OffsetDateTime.now());
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(path);
        errorResponse.setValidationErrors(validationErrors == null || validationErrors.isEmpty() ? null : validationErrors);
        return ResponseEntity.status(status).body(errorResponse);
    }

    private String buildJsonPath(java.util.List<JsonMappingException.Reference> path) {
        return path.stream()
                .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
                .collect(Collectors.joining("."));
    }

    private String mapAssertTrueField(String message, String objectName) {
        if (message == null) {
            return objectName;
        }

        if (message.contains("createdAt")) {
            return "createdAt";
        }
        if (message.contains("completedAt")) {
            return "completedAt";
        }
        if (message.contains("startDateTime")) {
            return "timeWindow.startDateTime";
        }
        if (message.contains("endDateTime")) {
            return "timeWindow.endDateTime";
        }
        return objectName;
    }
}