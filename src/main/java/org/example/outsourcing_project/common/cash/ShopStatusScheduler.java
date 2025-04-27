package org.example.outsourcing_project.common.cash;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.shop.service.ShopService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component

@RequiredArgsConstructor
public class ShopStatusScheduler {

    private final ShopService shopService;
    private final ShopRepository shopRepository;

    @Transactional
    @Scheduled(cron = "0 10 * * * *") // 매시 10분
    public void updateShopStars() {
        List<Shop> shops = shopRepository.findAll();
        for (Shop shop : shops) {
            ShopStatus calculatedStatus = shopService.calculateCurrentStatus(shop.getId(), LocalDateTime.now());

            if (shop.getShopStatusAuth() == ShopStatusAuth.MANUAL) {

                if (shop.getShopStatus() == calculatedStatus) {
                    shop.updateShopStatus(calculatedStatus, ShopStatusAuth.AUTO);
                }
                continue; // 수동이면 건너뜀 (수동 상태 유지)
            }

            if (shop.getShopStatus() != calculatedStatus) {
                shop.updateShopStatus(calculatedStatus, ShopStatusAuth.AUTO);
            }
        }
    }

}