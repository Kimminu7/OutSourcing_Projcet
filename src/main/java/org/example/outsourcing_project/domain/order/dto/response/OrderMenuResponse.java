package org.example.outsourcing_project.domain.order.dto.response;

import lombok.Getter;

import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.order.entity.OrderMenu;

/**
 * 주문한 메뉴 하나에 대한 응답 DTO 클래스입니다.
 */
@Getter
public class OrderMenuResponse {

    private Long menuId;
    private String menuName;
    private int price;

    public OrderMenuResponse(Menu menu) {
        this.menuId = menu.getId();
        this.menuName = menu.getName();
        this.price = menu.getPrice();
    }
}
