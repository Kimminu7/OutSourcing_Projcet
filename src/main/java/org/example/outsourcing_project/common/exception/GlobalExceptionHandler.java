package org.example.outsourcing_project.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getErrorCode(), request.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", Optional.ofNullable(error.getDefaultMessage()).orElse("Validation failed")
                ))
                .collect(Collectors.toList());

        return buildErrorResponse(ErrorCode.VALID_ERROR, request.getRequestURI(),fieldErrors);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode, String path, List<Map<String, String>> fieldErrors) {
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getMessage(),
                errorCode.getErrorCode(),
                errorCode.getHttpStatus(),
                errorCode.getHttpStatus().getReasonPhrase(),
                path,
                LocalDateTime.now(),
                fieldErrors
        );
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}

