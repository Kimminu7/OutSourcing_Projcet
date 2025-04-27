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
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final MenuRepository menuRepository;
	private final OrderRepository orderRepository;
	private final OrderStatusLogRepository orderStatusLogRepository;

	public OrderResponse createOrder(Long userId, OrderCreateRequest request) {
		// 메뉴 조회 및 검증
		Menu menu = menuRepository.findById(request.getMenuId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 메뉴입니다."));

		// 가게 정보 확인
		Shop shop = menu.getShop();
		if (!shop.isStatus()) {
			throw new IllegalStateException("해당 가게는 현재 영업 중이 아닙니다.");
		}

		// 영업 시간 체크
		LocalTime now = LocalTime.now();
		if (now.isBefore(shop.getStartTime()) || now.isAfter(shop.getEndTime())) {
			throw new IllegalStateException("가게의 영업 시간이 아닙니다.");
		}

		// 총 가격 계산 및 최소 금액 체크
		int totalPrice = menu.getPrice() * request.getQuantity();
		if (totalPrice < shop.getMinDeliveryPrice()) {
			throw new IllegalArgumentException("최소 주문 금액을 만족하지 않습니다.");
		}

		// 주문 생성
		Order order = new Order(userId, shop.getShopId(), menu.getId(), request.getQuantity(), totalPrice);
		orderRepository.save(order);

		return new OrderResponse(order);
	}

	/**
	 * 주문 상태를 변경합니다.
	 *
	 * @param orderId 주문 ID
	 * @param request 상태 변경 요청 DTO
	 */
	public void updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

		OrderStatus newStatus = request.getStatus();

		// 상태 변경 유효성 검사 (예: ORDERED → ACCEPTED → DELIVERING → DELIVERED)
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
				.build()
			)
			.toList();
	}

}