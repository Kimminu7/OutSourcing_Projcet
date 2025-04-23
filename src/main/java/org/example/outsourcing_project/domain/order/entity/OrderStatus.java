package org.example.outsourcing_project.domain.order.entity;

public enum OrderStatus {
	ORDERED,     // 주문 완료
	ACCEPTED,    // 사장님 수락
	COOKING,     // 조리 중
	DELIVERING,  // 배달 중
	DELIVERED    // 배달 완료
}