package org.example.outsourcing_project.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long storeId;

	@Column(nullable = false)
	private Long menuId;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Integer totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@Column(nullable = false)
	private LocalDateTime orderedAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	public Order(Long userId, Long storeId, Long menuId, Integer quantity, Integer totalPrice) {
		this.userId = userId;
		this.storeId = storeId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = OrderStatus.ORDERED;
		this.orderedAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void updateStatus(OrderStatus status) {
		this.status = status;
		this.updatedAt = LocalDateTime.now();
	}
}