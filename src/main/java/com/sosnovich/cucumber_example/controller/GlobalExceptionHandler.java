package com.sosnovich.cucumber_example.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sosnovich.cucumber_example.generated.model.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .details("Order not found")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle HttpMessageNotReadableException (e.g., invalid enum or incorrect JSON format)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        ApiErrorResponse errorResponse;

        if (cause instanceof InvalidFormatException formatException) {

            // If it's due to an invalid enum or type mismatch
            String invalidValue = formatException.getValue().toString();
            String targetType = formatException.getTargetType().getSimpleName();

            String message = "Invalid value for field of type " + targetType + ": " + invalidValue;

            // Provide field name if available
            String fieldNames = formatException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining(", "));

            String details = "Invalid value for field(s): " + fieldNames;

            errorResponse = ApiErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(message)
                    .timestamp(OffsetDateTime.now())
                    .details(details)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        if (cause instanceof JsonMappingException mappingException) {

            // Capture path and details about the error
            String fieldNames = mappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining(", "));

            int line = mappingException.getLocation().getLineNr();
            int column = mappingException.getLocation().getColumnNr();

            String details = "Invalid format in field(s): " + fieldNames + " at line " + line + ", column " + column;

            errorResponse = ApiErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Malformed JSON request")
                    .timestamp(OffsetDateTime.now())
                    .details(details)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Fallback for other cases
        errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request payload")
                .details("Check the request body for invalid fields or structure.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    // Handle validation errors (ConstraintViolationException)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        // Collect all validation error messages in a user-friendly format
        String violations = ex.getConstraintViolations().stream()
                .map(this::formatViolationMessage)
                .collect(Collectors.joining(", "));

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .timestamp(OffsetDateTime.now())
                .details(violations)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Helper method to format the violation message
    private String formatViolationMessage(ConstraintViolation<?> violation) {
        var fieldNames = violation.getPropertyPath().toString().split("\\.");
        String fieldName = fieldNames[fieldNames.length-2].concat(".".concat(fieldNames[fieldNames.length-1])); // Get the field name
        String message = violation.getMessage(); // Get the validation message

        // Format it in a user-friendly way
        return String.format("Field '%s': %s", fieldName, message);
    }
}
