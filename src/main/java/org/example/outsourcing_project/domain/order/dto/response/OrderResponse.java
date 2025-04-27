package org.example.outsourcing_project.domain.order.dto.response;

import lombok.Getter;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderMenu;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 주문 응답 데이터를 담는 DTO 클래스입니다.
 * 고객 또는 사장님에게 주문 정보를 반환할 때 사용됩니다.
 */
@Getter
public class OrderResponse {

	private Long orderId;
	private Long userId;
	private Long storeId;
	private List<OrderMenuResponse> orderMenus; // 변경된 부분
	private OrderStatus status;
	private LocalDateTime orderedAt;

	public OrderResponse(Order order) {
		this.orderId = order.getId();
		this.userId = order.getUser().getId();
		this.storeId = order.getShop().getId();
		this.orderMenus = order.getOrderMenus().stream()
				.map(OrderMenuResponse::new)
				.collect(Collectors.toList());
		this.status = order.getStatus();
		this.orderedAt = order.getCreatedAt();
	}
}
