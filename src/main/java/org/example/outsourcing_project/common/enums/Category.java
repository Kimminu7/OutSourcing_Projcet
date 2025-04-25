package org.example.outsourcing_project.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Category {
    KOREA("한식"),
    JAPAN("일식"),
    SNACKBAR("분식"),
    PIZZA("피자"),
    CHICKEN("치킨"),
    DESSERT("디저트"),
    CAFE("카페");

    public final String label;

    @JsonCreator
    public static Category from(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("입력값이 null이거나 공백입니다.");
        }
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(input) || c.label.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리: " + input));
    }

}
