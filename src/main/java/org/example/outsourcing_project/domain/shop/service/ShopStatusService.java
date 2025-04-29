package org.example.outsourcing_project.domain.shop.service;

public interface ShopStatusService {
        void closeShop(Long shopId, Long userId );

        void openShop(Long shopId,Long userId);

}
