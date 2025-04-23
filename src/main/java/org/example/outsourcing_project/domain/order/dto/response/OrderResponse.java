package org.example.outsourcing_project.domain.order.dto.response;

import lombok.Getter;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;

/**
 * 주문 응답 데이터를 담는 DTO 클래스입니다.
 * 고객 또는 사장님에게 주문 정보를 반환할 때 사용됩니다.
 */
@Getter
public class OrderResponse {

	private Long orderId;
	private Long userId;
	private Long storeId;
	private Long menuId;
	private Integer quantity;
	private Integer totalPrice;
	private OrderStatus status;
	private LocalDateTime orderedAt;
	private LocalDateTime updatedAt;

	public OrderResponse(Order order) {
		this.orderId = order.getId();
		this.userId = order.getUserId();
		this.storeId = order.getStoreId();
		this.menuId = order.getMenuId();
		this.quantity = order.getQuantity();
		this.totalPrice = order.getTotalPrice();
		this.status = order.getStatus();
		this.orderedAt = order.getOrderedAt();
		this.updatedAt = order.getUpdatedAt();
	}
}
