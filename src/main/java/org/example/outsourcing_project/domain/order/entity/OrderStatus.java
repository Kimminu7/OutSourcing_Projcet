package org.example.outsourcing_project.domain.order.entity;

public enum OrderStatus {
	ORDERED,       // 주문 완료
	ACCEPTED,      // 사장님이 주문 수락
	COOKING,       // 조리 중
	DELIVERING,    // 배달 중
	DELIVERED;     // 배달 완료

	public boolean canTransitionTo(OrderStatus target) {
		return switch (this) {
			case ORDERED -> target == ACCEPTED;
			case ACCEPTED -> target == COOKING;
			case COOKING -> target == DELIVERING;
			case DELIVERING -> target == DELIVERED;
			default -> false;
		};
	}
}
