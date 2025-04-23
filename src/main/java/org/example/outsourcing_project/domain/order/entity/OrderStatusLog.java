package org.example.outsourcing_project.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.example.outsourcing_project.domain.order.entity.OrderStatus;

@Entity
@Getter
@NoArgsConstructor
public class OrderStatusLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long orderId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus previousStatus;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus newStatus;

	@Column(nullable = false)
	private Long storeId;

	@Column(nullable = false)
	private LocalDateTime changedAt;

	public OrderStatusLog(Long orderId, OrderStatus previousStatus, OrderStatus newStatus, Long storeId) {
		this.orderId = orderId;
		this.previousStatus = previousStatus;
		this.newStatus = newStatus;
		this.storeId = storeId;
		this.changedAt = LocalDateTime.now();
	}
}
