package org.example.outsourcing_project.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import org.example.outsourcing_project.common.exception.custom.BaseException;

import java.util.Arrays;

import static org.example.outsourcing_project.common.exception.ErrorCode.INVALID_CATEGORY;

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
            throw new BaseException(INVALID_CATEGORY);
        }
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(input) || c.label.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new BaseException(INVALID_CATEGORY));
    }

}
