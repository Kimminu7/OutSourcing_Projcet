package org.example.outsourcing_project.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderStatusLogResponse {

	private OrderStatus previousStatus;
	private OrderStatus newStatus;
	private LocalDateTime changedAt;
}
