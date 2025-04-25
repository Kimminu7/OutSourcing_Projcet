package org.example.outsourcing_project.domain.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopWithMenuResponse {
    private String storeName;
    private String address;
    private ShopStatus shopStatus;
    private double star;
    private long minDeliverPrice;
    private List<MenuItem> menus;

    public static class MenuItem {
        private String menuName;
        private int price;

        public MenuItem(String menuName, int price){
            this.menuName=menuName;
            this.price=price;
        }
    }
    public static ShopWithMenuResponse from(Shop shop,List<MenuItem> menuItems){
        return ShopWithMenuResponse.builder()
                .storeName(shop.getShopName())
                .address(shop.getAddress())
                .shopStatus(shop.getShopStatus())
                .star(shop.getStars())
                .minDeliverPrice(shop.getMinDeliveryPrice())
                .menus(menuItems)
                .build();
    }
}

