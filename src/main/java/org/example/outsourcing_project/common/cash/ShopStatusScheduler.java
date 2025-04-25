package org.example.outsourcing_project.common.cash;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Component

@RequiredArgsConstructor
public class ShopStatusScheduler {

    private final ShopRepository shopRepository;

    @Transactional
    @Scheduled(cron = "0 10 * * * *") // 매시 정각
    public void updateShopStars() {
        List<Shop> shops = shopRepository.findAll();
        for (Shop shop : shops) {
            shop.updateShopStatus(shop.calculateCurrentStatus(LocalTime.now()));
        }
    }
}