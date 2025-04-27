package org.example.outsourcing_project.domain.order.dto.response;

import lombok.Getter;
import org.example.outsourcing_project.domain.order.entity.OrderMenu;

/**
 * 주문한 메뉴 하나에 대한 응답 DTO 클래스입니다.
 */
@Getter
public class OrderMenuResponse {

    private Long menuId;
    private Integer quantity;
    private Integer price;

    public OrderMenuResponse(OrderMenu orderMenu) {
        this.menuId = orderMenu.getMenuId();
        this.quantity = orderMenu.getQuantity();
        this.price = orderMenu.getPrice();
    }
}
