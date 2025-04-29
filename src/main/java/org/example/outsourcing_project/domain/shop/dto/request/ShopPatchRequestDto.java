package org.example.outsourcing_project.domain.shop.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ShopPatchRequestDto {

    @Pattern(
            regexp = "^(01[016789]|0\\d{1,2})-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식이어야 합니다. 예: 010-1234-5678"
    )
    private String storeNumber;

    @PositiveOrZero(message = "0원 이상이어야 합니다.")
    private Long minDeliveryPrice;

    private LocalTime openTime;

    private LocalTime closeTime;
}
