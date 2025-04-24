package org.example.outsourcing_project.domain.shop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class ShopWithMenuResponse {
    private String storeName;
    private String address;
    private boolean open;
    private double star;
    private long minDeliverPrice;
    private List<MenuItem> menu;

    public static class MenuItem {
        private String menuName;
        private int price;
        MenuItem(String menuName,int price){
            this.menuName=menuName;
            this.price=price;
        }
    }
    public ShopWithMenuResponse(String storeName, String address, boolean open, double star, long minDeliverPrice){
        this.address=address;
        this.storeName=storeName;
        this.open=open;
        this.minDeliverPrice=minDeliverPrice;
        this.star=star;
    }
}


