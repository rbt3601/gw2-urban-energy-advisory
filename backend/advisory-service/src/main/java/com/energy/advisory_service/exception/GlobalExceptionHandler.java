package com.energy.advisory_service.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> fieldErrors.putIfAbsent(error.getObjectName(), error.getDefaultMessage()));

        ApiErrorResponse response = buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "One or more request fields are missing or invalid",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        Throwable root = getRootCause(ex);
        String message = "Malformed JSON request";

        if (root instanceof InvalidFormatException invalidFormatException) {
            String fieldPath = invalidFormatException.getPath()
                    .stream()
                    .map(ref -> ref.getFieldName())
                    .filter(name -> name != null && !name.isBlank())
                    .collect(Collectors.joining("."));

            String rawValue = String.valueOf(invalidFormatException.getValue());

            if (root.getMessage() != null && root.getMessage().contains("Invalid property type")) {
                message = root.getMessage() + ". Allowed values: home, smallBuilding";
            } else if (root.getMessage() != null && root.getMessage().contains("Invalid granularity")) {
                message = root.getMessage() + ". Allowed values: hourly, daily";
            } else if (root.getMessage() != null && root.getMessage().contains("Invalid assessment status")) {
                message = root.getMessage() + ". Allowed values: Completed, Partial, Failed";
            } else if (!fieldPath.isBlank()) {
                message = "Invalid value '" + rawValue + "' for field '" + fieldPath + "'";
            }
        } else if (root instanceof MismatchedInputException mismatchedInputException) {
            String fieldPath = mismatchedInputException.getPath()
                    .stream()
                    .map(ref -> ref.getFieldName())
                    .filter(name -> name != null && !name.isBlank())
                    .collect(Collectors.joining("."));

            if (!fieldPath.isBlank()) {
                message = "Invalid or missing value for field '" + fieldPath + "'";
            }
        } else if (root instanceof IllegalArgumentException illegalArgumentException) {
            String rootMessage = illegalArgumentException.getMessage();
            if (rootMessage != null && !rootMessage.isBlank()) {
                if (rootMessage.contains("Invalid property type")) {
                    message = rootMessage + ". Allowed values: home, smallBuilding";
                } else if (rootMessage.contains("Invalid granularity")) {
                    message = rootMessage + ". Allowed values: hourly, daily";
                } else if (rootMessage.contains("Invalid assessment status")) {
                    message = rootMessage + ". Allowed values: Completed, Partial, Failed";
                } else {
                    message = rootMessage;
                }
            }
        }

        ApiErrorResponse response = buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                message,
                request.getRequestURI(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath() != null
                    ? violation.getPropertyPath().toString()
                    : "request";
            fieldErrors.putIfAbsent(field, violation.getMessage());
        });

        ApiErrorResponse response = buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "Constraint violation",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ResponseStatusException.class, ErrorResponseException.class})
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatusCode statusCode;
        String reason;

        if (ex instanceof ResponseStatusException responseStatusException) {
            statusCode = responseStatusException.getStatusCode();
            reason = responseStatusException.getReason();
        } else {
            ErrorResponseException errorResponseException = (ErrorResponseException) ex;
            statusCode = errorResponseException.getStatusCode();
            reason = errorResponseException.getBody() != null
                    ? errorResponseException.getBody().getDetail()
                    : ex.getMessage();
        }

        HttpStatus status = HttpStatus.valueOf(statusCode.value());

        ApiErrorResponse response = buildResponse(
                status,
                status.getReasonPhrase(),
                reason != null && !reason.isBlank() ? reason : "Request failed",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ApiErrorResponse buildResponse(
            HttpStatus status,
            String error,
            String message,
            String path,
            Map<String, String> fieldErrors
    ) {
        return new ApiErrorResponse(
                OffsetDateTime.now(),
                status.value(),
                error,
                message,
                path,
                fieldErrors
        );
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable result = throwable;
        while (result.getCause() != null && result.getCause() != result) {
            result = result.getCause();
        }
        return result;
    }
}