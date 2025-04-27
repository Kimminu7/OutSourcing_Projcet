package org.example.outsourcing_project.domain.shop.service;

import org.example.outsourcing_project.domain.shop.dto.response.ShopDeliveryResponseDto;

import java.util.List;

public interface ShopDeliveryService {
    void deliveryAccept(Long orderId,Long shopId,Long userId);

    void startCooking(Long orderId,Long shopId,Long userId);

    void startDelivering(Long orderId,Long shopId,Long userId);

    List<ShopDeliveryResponseDto> findAll (Long shopId,Long userId);

    ShopDeliveryResponseDto findOrder (Long orderId,Long shopId,Long userId);
}
