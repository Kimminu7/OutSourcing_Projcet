package org.example.outsourcing_project.domain.shop.service;

import lombok.RequiredArgsConstructor;
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
        if (order.getStatus()!=OrderStatus.ORDERED){
            throw new RuntimeException("주문이 생성되지 않았습니다");
        }
        order.updateStatus(OrderStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public void startCooking(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);
        Order order = validateOrder(orderId, shopId);
        if (order.getStatus()!=OrderStatus.ACCEPTED){
            throw new RuntimeException("주문을 받지 않았습니다");
        }
        order.updateStatus(OrderStatus.COOKING);
    }

    @Override
    @Transactional
    public void startDelivering(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);
        Order order = validateOrder(orderId, shopId);
        if (order.getStatus()!=OrderStatus.ORDERED){
            throw new RuntimeException("요리를 시작하지 않았습니다");
        }
        order.updateStatus(OrderStatus.DELIVERING);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopDeliveryResponseDto> findAll(Long shopId, Long userId) {

        //아직 commit 안함
        validateShop(userId, shopId);
        //오더를 가져야 합니다.
        List<Order> orders = orderRepository.findByShopId(shopId); // ❗ 가게 주문만 조회해야 함

        return orders.stream()
                .map(ShopDeliveryResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ShopDeliveryResponseDto findOrder(Long orderId, Long shopId, Long userId) {
        validateShop(userId, shopId);

        Order order = orderRepository.findByIdWithUserAndShop(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        if (!order.getId().equals(shopId)) {
            throw new NotFoundShop();
        }

        return ShopDeliveryResponseDto.from(order);
    }


    // orderId, shopId 매칭 검증
    private Order validateOrder(Long orderId, Long shopId) {
        Order order = orderRepository.findByIdWithUserThrowException(orderId);
        if (!order.getShop().getId().equals(shopId)) {
            throw new NotFoundShop();
        }
        return order;
    }

    // userId (사장님) 검증
    private void validateShop(Long userId, Long shopId) {
        Shop shop = shopRepository.findByIdThrowException(shopId);
        if (!shop.getUser().getId().equals(userId)) {
            throw new ForbiddenOwner();
        }


    }
}
