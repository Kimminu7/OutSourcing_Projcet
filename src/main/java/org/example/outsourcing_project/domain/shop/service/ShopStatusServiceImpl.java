package org.example.outsourcing_project.domain.shop.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
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
        if (shop.getShopStatus()==ShopStatus.CLOSED) {
            throw new RuntimeException("이미 폐점 했습니다");
        }
        shop.updateShopStatus(ShopStatus.CLOSED, ShopStatusAuth.MANUAL);
    }

    @Override
    @Transactional
    public void openShop(Long shopId) {
        Shop shop=shopRepository.findByIdThrowException(shopId);
        if (shop.getShopStatus()==ShopStatus.OPEN) {
            throw new RuntimeException("이미 오픈 했습니다");
        }
        shop.updateShopStatus(ShopStatus.OPEN,ShopStatusAuth.MANUAL);
    }
}
