package org.example.outsourcing_project.domain.shop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.domain.shop.entity.OperatingHours;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.user.entity.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ShopRequestDto {

    @NotBlank(message = "가게이름은 빈칸이면 안됩니다.")
    private String shopName;
    @NotBlank(message = "가게주소는 빈칸이면 안됩니다.")
    private String address;
    @NotBlank(message = "가게번호는 필수입니다")
    @Pattern(
            regexp = "^(01[016789]|0\\d{1,2})-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식이어야 합니다. 예: 010-1234-5678"
    )
    private String shopNumber;
    @Builder.Default//0으로 디폴트 설정
    @PositiveOrZero(message = "최소주문값은 0이상입니다.")
    private long minDeliveryPrice =0L;

    @NotNull(message = "영업시작 시간을 말해주세요")
    private LocalTime openTime;

    @NotNull(message = "영업종료 시간을 말해주세요")
    private LocalTime closeTime;
    @Builder.Default
    private List<ShopDayOfWeek> closedDays=new ArrayList<>();
    private Category category;

    public Shop toEntity(User user) {
        return Shop.builder()
                .shopName(shopName)
                .address(address)
                .shopNumber(shopNumber)
                .operatingHours(new OperatingHours(openTime, closeTime))
                .minDeliveryPrice(minDeliveryPrice)
                .category(category)
                .closedDays(closedDays)
                .shopStatus(ShopStatus.CLOSED)
                .user(user)
                .build();
    }

}
