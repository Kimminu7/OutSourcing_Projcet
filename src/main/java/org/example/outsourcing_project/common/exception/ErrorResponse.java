package org.example.outsourcing_project.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class ErrorResponse {
    private String message;
    private String code;
    private HttpStatus status;
    private String error;
    private String path;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Map<String, String>> fieldErrors;

    public ErrorResponse(String message, String code, HttpStatus status, String error,
                         String path, LocalDateTime timestamp, List<Map<String, String>> fieldErrors) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
        this.fieldErrors = fieldErrors;
    }

    // Getter, Setter 생략 가능 (Lombok 사용하는 경우 @Data 또는 @Getter/@Setter 사용 가능)
}

