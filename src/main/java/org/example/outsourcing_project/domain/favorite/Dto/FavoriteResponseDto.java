package org.example.outsourcing_project.domain.favorite.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;


//필요한거 있으시면 더 말씀부탁드립니다.
@Builder
@AllArgsConstructor
@Getter
public class FavoriteResponseDto {
    String shopName;
    ShopStatus shopStatus;

    public static FavoriteResponseDto of(Shop shop){
        return FavoriteResponseDto.builder()
                .shopName(shop.getShopName())
                .shopStatus(shop.getShopStatus())
                .build();
    }
}
