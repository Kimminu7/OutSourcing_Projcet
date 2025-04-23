package org.example.outsourcing_project.domain.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 생성 요청 데이터를 담는 DTO 클래스입니다.
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequest {

	@NotNull(message = "가게 ID는 필수입니다.")
	private Long storeId;

	@NotNull(message = "메뉴 ID는 필수입니다.")
	private Long menuId;

	@NotNull(message = "수량은 필수입니다.")
	@Min(value = 1, message = "주문 수량은 1 이상이어야 합니다.")
	private Integer quantity;

	public OrderCreateRequest(Long storeId, Long menuId, Integer quantity) {
		this.storeId = storeId;
		this.menuId = menuId;
		this.quantity = quantity;
	}
}
