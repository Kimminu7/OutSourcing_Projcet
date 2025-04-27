package org.example.outsourcing_project.domain.shop.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class ShopDeliveryResponseDto {

    String shopName;
    String userName;
    String userAddress;
    LocalDateTime localDateTime;
    OrderStatus orderStatus;

    public static ShopDeliveryResponseDto from(Order order){

        return ShopDeliveryResponseDto.builder()
                .shopName(order.getShop().getShopName())
                .userName(order.getUser().getName())
                .userAddress(order.getUser().getAddress())
                .localDateTime(order.getCreatedAt())
                .orderStatus(order.getStatus())
                .build();

    }

}
