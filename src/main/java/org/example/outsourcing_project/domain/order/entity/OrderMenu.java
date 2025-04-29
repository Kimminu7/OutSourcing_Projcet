package org.example.outsourcing_project.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "order_menus")
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price; // 단가 * 수량 결과

    // Order 세팅 편의 메서드
    public void setOrder(Order order) {
        this.order = order;
    }

    // 가격 자동 계산을 위한 생성자
    @Builder
    public OrderMenu(Order order, Long menuId, Integer quantity, Integer menuPrice) {
        this.order = order;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = menuPrice * quantity;
    }
}

