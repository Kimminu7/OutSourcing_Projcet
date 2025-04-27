package org.example.outsourcing_project.domain.order.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.outsourcing_project.domain.order.dto.request.OrderStatusUpdateRequest;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.entity.OrderStatusLog;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.order.repository.OrderStatusLogRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OrderStatusLogAspect {

	private final OrderRepository orderRepository;
	private final OrderStatusLogRepository orderStatusLogRepository;

	@Pointcut("execution(* org.example.outsourcing_project.domain.order.service.OrderService.updateOrderStatus(..))")
	public void updateOrderStatusMethod() {}

	@AfterReturning("updateOrderStatusMethod() && args(orderId, request)")
	public void logOrderStatusChange(Long orderId, Object request) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

		// 로그 저장
		OrderStatusLog savedLog = new OrderStatusLog(
			order.getId(),
			order.getStatus(),
			((OrderStatusUpdateRequest) request).getStatus(),
			order.getStoreId()
		);

		orderStatusLogRepository.save(savedLog);
		log.info("✅ 주문 상태 로그 저장: 주문ID={}, 상태={}, 시간={}",
			order.getId(), savedLog.getNewStatus(), savedLog.getChangedAt());
}
