package org.example.outsourcing_project.domain.shop.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.dto.response.ShopDeliveryResponseDto;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.exception.ForbiddenOwner;
import org.example.outsourcing_project.domain.shop.exception.NotFoundShop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopDeliveryServiceImpl implements ShopDeliveryService {

    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public void deliveryAccept(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);
        Order order = validateOrder(orderId, shopId);
        if (order.getStatus() != OrderStatus.ORDERED) {
            throw new BaseException(ErrorCode.NOT_FOUND_ORDER);
        }
        order.updateStatus(OrderStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public void startCooking(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);
        Order order = validateOrder(orderId, shopId);
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new BaseException(ErrorCode.ORDER_NOT_STARTED);
        }
        order.updateStatus(OrderStatus.COOKING);
    }

    @Override
    @Transactional
    public void startDelivering(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);
        Order order = validateOrder(orderId, shopId);
        if (order.getStatus() != OrderStatus.COOKING) {
            throw new BaseException(ErrorCode.ORDER_NOT_CREATED);
        }
        order.updateStatus(OrderStatus.DELIVERING);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopDeliveryResponseDto> findAll(Long shopId, Long userId) {
        validateShop(userId, shopId);
        List<Order> orders = orderRepository.findByShopId(shopId);
        return orders.stream()
                .map(ShopDeliveryResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ShopDeliveryResponseDto findOrder(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);

        Order order = orderRepository.findByIdWithUserAndShop(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER_ID));

        if (!order.getShop().getId().equals(shopId)) {
            throw new NotFoundShop();
        }

        return ShopDeliveryResponseDto.from(order);
    }

    private Order validateOrder(Long orderId, Long shopId) {
        Order order = orderRepository.findByIdWithUserThrowException(orderId);
        if (!order.getShop().getId().equals(shopId)) {
            throw new NotFoundShop();
        }
        return order;
    }

    private void validateShop(Long userId, Long shopId) {
        Shop shop = shopRepository.findByIdThrowException(shopId);
        if (!shop.getUser().getId().equals(userId)) {
            throw new ForbiddenOwner();
        }
    }
}
