package org.example.outsourcing_project.domain.shop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;

import java.time.LocalTime;
import java.util.List;

@Getter
public class ShopRequestDto {

    @NotBlank(message = "가게이름은 빈칸이면 안됩니다.")
    private String storeName;
    @NotBlank(message = "가게주소는 빈칸이면 안됩니다.")
    private String address;
    @Pattern(
            regexp = "0(?:0|3)(?:0|1|[6-9])-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식이어야 합니다. 예: 010-1234-5678"
    )
    private String storeNumber;


    private long minDeliveryPrice;
    @NotNull(message = "영업시작 시간을 말해주세요")
    private LocalTime startTime;
    @NotNull(message = "영업종료 시간을 말해주세요")
    private LocalTime endTime;

    private List<ShopDayOfWeek> closedDays;
    private Category category;
}
