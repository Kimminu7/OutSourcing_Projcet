package org.example.outsourcing_project.domain.order.repository;

import org.example.outsourcing_project.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	// 필요한 쿼리는 이후에 추가 가능
}
