package org.example.outsourcing_project.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.common.entity.BaseTimeEntity;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "orders")
public class Order extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	// 1:N 관계 추가
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	//초기 생성용
	public Order(User user, Shop shop, Menu menu) {
		this.user = user;
		this.shop = shop;
		this.status = OrderStatus.ORDERED;
	}

	public void updateStatus(OrderStatus status) {
		this.status = status;
	}

}
