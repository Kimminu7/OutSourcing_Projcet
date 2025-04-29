package org.example.outsourcing_project.common.cash;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShopScheduler {

    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Scheduled(cron = "0 0 * * * *") // 매시 정각
    public void updateStars() {
        updateMenuStars();
        updateShopStars();
    }

    private void updateMenuStars() {
        List<Menu> menus = menuRepository.findAll();
        for (Menu menu : menus) {
            Double averageStar = orderRepository.calculateAverageStarByMenuId(menu.getId());
            if (averageStar == null) {
                averageStar = 5.0; // 리뷰 없으면 기본 5점
            }
            menu.updateStars(averageStar);
        }
    }

    private void updateShopStars() {
        List<Shop> shops = shopRepository.findAll();
        for (Shop shop : shops) {
            Double averageStar = menuRepository.calculateAverageStarByShopId(shop.getId());
            if (averageStar == null) {
                averageStar = 5.0; // 메뉴에 별점 없으면 기본 5점
            }
            shop.setStars(averageStar);
        }
    }
}
