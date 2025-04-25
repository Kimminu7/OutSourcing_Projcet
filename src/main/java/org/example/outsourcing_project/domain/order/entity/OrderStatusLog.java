package org.example.outsourcing_project.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;

/**
 * 주문 상태 변경 로그 엔티티
 */
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

	/**
	 * 주문 상태 변경 로그 생성자
	 *
	 * @param orderId        주문 ID
	 * @param previousStatus 변경 전 상태
	 * @param newStatus      변경 후 상태
	 * @param storeId        가게 ID
	 */
	public OrderStatusLog(Long orderId, OrderStatus previousStatus, OrderStatus newStatus, Long storeId) {
		this.orderId = orderId;
		this.previousStatus = previousStatus;
		this.newStatus = newStatus;
		this.storeId = storeId;
		this.changedAt = LocalDateTime.now();
	}
}
