package org.example.outsourcing_project.common.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.outsourcing_project.common.converter.DayOfWeekDeserializer;

import java.time.DayOfWeek;
import java.util.Arrays;

@AllArgsConstructor
@Getter

@JsonDeserialize(using = DayOfWeekDeserializer.class)
public enum ShopDayOfWeek {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일");

    public final String label;

    public static ShopDayOfWeek of(String input) {
        return Arrays.stream(values())
                .filter(d -> d.name().equalsIgnoreCase(input) || d.label.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요일입니다: " + input));
    }
}
