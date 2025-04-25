package org.example.outsourcing_project.common.cash;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShopStatusScheduler {

    private final ShopRepository shopRepository;

    @Scheduled(cron = "0 */5 * * * *") // 5분마다 실행
    @Transactional
    public void updateShopStatuses() {
        List<Shop> allShops = shopRepository.findAll();
        List<Shop> updatedShops = new ArrayList<>();

        for (Shop shop : allShops) {
            if (shop.needsStatusUpdate()) {
                shop.updateShopStatus(shop.getCurrentShopStatus());
                updatedShops.add(shop);
            }
        }

        // 변경된 것만 저장
        shopRepository.saveAll(updatedShops);
    }

}
