package org.example.outsourcing_project.domain.shop.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Service
public class ShopStatusServiceImpl implements ShopStatusService{

    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public void closeShop(Long shopId) {
        Shop shop=shopRepository.findByIdThrowException(shopId);
        shop.updateShopStatus(ShopStatus.CLOSED);
    }

    @Override
    @Transactional
    public void openShop(Long shopId) {
        Shop shop=shopRepository.findByIdThrowException(shopId);
        shop.updateShopStatus(ShopStatus.OPEN);
    }
}
