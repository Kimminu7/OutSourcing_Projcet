package org.example.outsourcing_project.common.category;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Category {
    KOREA("한식"),JAPAN("일식"),SNACKBAR("분식"),PIZZA("피자"),CHICKEN("치킨");

    public final String contents;

    public static Category of(String category){
        return Arrays.stream(Category.values())
                .filter(r -> r.name().equalsIgnoreCase(category))
                .findFirst()
                .orElseThrow(()->new RuntimeException("유효하지 않은 category"));

    }

}
