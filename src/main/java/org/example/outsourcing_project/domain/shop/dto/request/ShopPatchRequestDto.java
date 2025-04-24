package org.example.outsourcing_project.domain.shop.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ShopPatchRequestDto {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Pattern(
            regexp = "0(?:0|3)(?:0|1|[6-9])-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식이어야 합니다. 예: 010-1234-5678"
    )
    private String storeNumber;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @PositiveOrZero(message = "0원 이상이어야 합니다.")
    private Long minDeliveryPrice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalTime startTime;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalTime endTime;
}
