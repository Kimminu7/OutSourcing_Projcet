package org.example.outsourcing_project.domain.order.repository;

import org.example.outsourcing_project.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
	// 필요한 쿼리는 이후에 추가 가능
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.user WHERE o.id = :orderId")
    Optional<Order> findByIdWithUser(@Param("orderId") Long orderId); // ✅ 수정

    default Order findByIdWithUserThrowException(Long orderId) {
        return findByIdWithUser(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));
    }

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.shop LEFT JOIN FETCH o.user WHERE o.shop.id = :stopId")
    List<Order> findByShopId(@Param("storeId") Long shopId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.shop LEFT JOIN FETCH o.user WHERE o.user.id = :userId")
    List<Order> findByUserId(@Param("storeId") Long userId);


    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.shop LEFT JOIN FETCH o.user WHERE o.id = :orderId")
    Optional<Order> findByIdWithUserAndShop(@Param("orderId") Long orderId);


}

