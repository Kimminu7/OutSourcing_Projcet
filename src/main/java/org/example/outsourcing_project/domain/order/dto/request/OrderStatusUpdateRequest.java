package org.example.outsourcing_project.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;

/**
 * 주문 상태 변경 요청을 위한 DTO 클래스입니다.
 */
@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequest {

	@NotNull(message = "변경할 상태를 입력해주세요.")
	private OrderStatus status;

	public OrderStatusUpdateRequest(OrderStatus status) {
		this.status = status;
	}
}
