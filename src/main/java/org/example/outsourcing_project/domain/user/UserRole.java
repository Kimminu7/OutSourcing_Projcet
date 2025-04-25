package org.example.outsourcing_project.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

import java.util.Arrays;


@AllArgsConstructor

public enum UserRole {
    OWNER("사장"), // 사장
    USER ("사용자"); // 일반 사용자

    private final String label;


    @JsonCreator
    public static UserRole from(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("입력값이 null이거나 공백입니다.");
        }
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(input) || c.label.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리: " + input));
    }
}
