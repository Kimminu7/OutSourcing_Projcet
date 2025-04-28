package org.example.outsourcing_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.dto.response.ShopDeliveryResponseDto;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDeliveryService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void confirm(Long orderId, Long userId) {
        Order order = orderRepository.findByIdWithUser(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER_ID));

        if (order.getStatus() != OrderStatus.DELIVERING) {
            throw new BaseException(ErrorCode.ORDER_NOT_DELIVERED);
        }

        if (!Objects.equals(order.getUser().getId(), userId)) {
            throw new BaseException(ErrorCode.FORBIDDEN_ORDER_CONFIRM);
        }

        order.updateStatus(OrderStatus.DELIVERED);
    }

    @Transactional(readOnly = true)
    public List<ShopDeliveryResponseDto> findAll(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(ShopDeliveryResponseDto::from)
                .collect(Collectors.toList());
    }
}
