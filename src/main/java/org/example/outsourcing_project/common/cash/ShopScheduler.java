package org.example.outsourcing_project.common.cash;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component

@RequiredArgsConstructor
public class ShopScheduler {

    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *") // 매시 정각
    public void updateShopStars() {
        List<Shop> shops = shopRepository.findAll();
        for (Shop shop : shops) {
//            double newStar = menuRepository.calculateAverageStar(shop.getShopId());
            double newStar=5;
            shop.setStars(newStar);
        }
    }
}
