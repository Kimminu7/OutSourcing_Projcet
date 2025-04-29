package org.example.outsourcing_project.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 생성 요청 데이터를 담는 DTO 클래스입니다.
 */

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 주문 생성 요청 데이터를 담는 DTO 클래스입니다.
 * (여러 메뉴를 한 번에 주문 가능)
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequest {

	@NotEmpty(message = "주문할 메뉴 목록은 비어 있을 수 없습니다.")
	@Valid
	private List<OrderMenuCreateRequest> orderMenus;

	public OrderCreateRequest(List<OrderMenuCreateRequest> orderMenus) {
		this.orderMenus = orderMenus;
	}
}

