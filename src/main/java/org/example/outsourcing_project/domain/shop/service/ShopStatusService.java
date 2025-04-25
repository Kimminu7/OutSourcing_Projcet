package org.example.outsourcing_project.domain.shop.service;

public interface ShopStatusService {
        void closeShop(Long shopId);

        void openShop(Long shopId);

}
