package org.example.outsourcing_project.domain.order.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.order.dto.response.OrderStatusLogResponse;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.dto.request.OrderCreateRequest;
import org.example.outsourcing_project.domain.order.dto.request.OrderStatusUpdateRequest;
import org.example.outsourcing_project.domain.order.dto.response.OrderResponse;
import org.example.outsourcing_project.domain.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	/**
	 * 주문 생성 API
	 *
	 * @param request 주문 요청 DTO
	 * @param userId  JWT에서 추출한 사용자 ID (임시로 파라미터로 받음)
	 * @return 생성된 주문 정보
	 */
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(
			@RequestBody @Valid OrderCreateRequest request,
			@LoginUser Long userId  // 실제론 @AuthenticationPrincipal 등으로 받음
	) {
		OrderResponse response = orderService.createOrder(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * 주문 상태 변경 API
	 *
	 * @param orderId 주문 ID
	 * @param request 상태 변경 요청 DTO
	 */
	@PatchMapping("/{orderId}/status")
	public ResponseEntity<Void> updateOrderStatus(
		@PathVariable Long orderId,
		@RequestBody @Valid OrderStatusUpdateRequest request
	) {
		orderService.updateOrderStatus(orderId, request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{orderId}/logs")
	public ResponseEntity<List<OrderStatusLogResponse>> getOrderLogs(@PathVariable Long orderId) {
		List<OrderStatusLogResponse> logs = orderService.getOrderLogs(orderId);
		return ResponseEntity.ok(logs);
	}

}
