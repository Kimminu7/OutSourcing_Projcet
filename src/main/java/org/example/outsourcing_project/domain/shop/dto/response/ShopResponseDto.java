package org.example.outsourcing_project.domain.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;

@Getter
@AllArgsConstructor
@Builder
public class ShopResponseDto {
    private String storeName;
    private String address;
    private ShopStatus shopStatus;
    private double star;
    private long minDeliverPrice;

    public static ShopResponseDto from(Shop shop){
        return ShopResponseDto.builder()
                .storeName(shop.getShopName())
                .address(shop.getAddress())
                .shopStatus(shop.getShopStatus())
                .star(shop.getStars())
                .minDeliverPrice(shop.getMinDeliveryPrice())
                .build();
    }
}

