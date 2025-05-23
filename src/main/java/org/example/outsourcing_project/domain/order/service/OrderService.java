package org.example.outsourcing_project.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.order.dto.request.OrderCreateRequest;
import org.example.outsourcing_project.domain.order.dto.request.OrderStatusUpdateRequest;
import org.example.outsourcing_project.domain.order.dto.response.OrderResponse;
import org.example.outsourcing_project.domain.order.dto.response.OrderStatusLogResponse;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.entity.OrderStatusLog;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.order.repository.OrderStatusLogRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.shop.service.ShopService;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final MenuRepository menuRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderStatusLogRepository orderStatusLogRepository;
	private final ShopService shopService;
	private final ShopRepository shopRepository;

	public OrderResponse createOrder(Long userId, OrderCreateRequest request) {
		User user = userRepository.findByIdOrElseThrow(userId);

		// 첫 번째 메뉴로 가게 조회
		Long menuId = request.getOrderMenus().get(0).getMenuId();
		Menu firstMenu = menuRepository.findById(menuId)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 메뉴입니다."));

		Shop shop = shopRepository.findActiveShopByIdThrowException(firstMenu.getShop().getId());

		// 가게 상태 확인
		if (shopService.calculateCurrentStatus(shop.getId(),LocalDateTime.now())!= ShopStatus.OPEN&&shop.getShopStatusAuth()== ShopStatusAuth.AUTO){
			throw new IllegalStateException("해당 가게는 현재 영업 중이 아닙니다.");
		}
		if (shop.getShopStatus()!= ShopStatus.OPEN&&shop.getShopStatusAuth()== ShopStatusAuth.MANUAL){
			throw new IllegalStateException("해당 가게는 현재 영업 중이 아닙니다.");
		}

		// 주문 생성
		Order order = new Order(user, shop, firstMenu);  // 메뉴를 추가

		// 최소 배달 금액 확인
		if (firstMenu.getPrice() < shop.getMinDeliveryPrice()) {
			throw new IllegalArgumentException("최소 주문 금액을 만족하지 않습니다.");
		}

		orderRepository.save(order);

		return new OrderResponse(order);
	}

	public void updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

		OrderStatus newStatus = request.getStatus();

		if (!order.getStatus().canTransitionTo(newStatus)) {
			throw new IllegalStateException("유효하지 않은 주문 상태 변경입니다.");
		}

		order.updateStatus(newStatus);

		// (AOP에서 로그 기록 예정)
	}

	public List<OrderStatusLogResponse> getOrderLogs(Long orderId) {
		List<OrderStatusLog> logs = orderStatusLogRepository.findByOrderIdOrderByChangedAtAsc(orderId);

		return logs.stream()
				.map(log -> OrderStatusLogResponse.builder()
						.previousStatus(log.getPreviousStatus())
						.newStatus(log.getNewStatus())
						.changedAt(log.getChangedAt())
						.build())
				.toList();
	}
}
