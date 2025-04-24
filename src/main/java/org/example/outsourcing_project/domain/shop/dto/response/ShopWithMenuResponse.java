package org.example.outsourcing_project.domain.shop.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ShopWithMenuResponse {
    private String storeName;
    private String address;
    private boolean open;
    private int star;
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
    ShopWithMenuResponse(String storeName,String address,boolean open,int star,long minDeliverPrice){
        this.address=address;
        this.storeName=storeName;
        this.open=open;
        this.minDeliverPrice=minDeliverPrice;
        this.star=star;
    }
}