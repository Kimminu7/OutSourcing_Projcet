package org.example.outsourcing_project.domain.order.repository;

import org.example.outsourcing_project.domain.order.entity.OrderStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, Long> {
	// 상태 로그는 기본 save(), findAll() 정도만 사용 예정
}
