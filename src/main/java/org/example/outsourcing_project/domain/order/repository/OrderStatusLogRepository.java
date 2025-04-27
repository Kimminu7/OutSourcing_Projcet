package org.example.outsourcing_project.domain.order.repository;

import java.util.List;

import org.example.outsourcing_project.domain.order.entity.OrderStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, Long> {
	List<OrderStatusLog> findByOrderIdOrderByChangedAtAsc(Long orderId);
}
