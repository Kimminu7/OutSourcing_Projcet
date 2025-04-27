package org.example.outsourcing_project.domain.shop.controller;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.shop.dto.response.ShopDeliveryResponseDto;
import org.example.outsourcing_project.domain.shop.service.ShopDeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops/{shopId}")
@RequiredArgsConstructor
public class ShopDeliveryController {
    private final ShopDeliveryService shopDeliveryService;

    @PatchMapping("/{orderId/accept}")
    public ResponseEntity<Void> deliveryAccept(@PathVariable Long shopId,
                                       @PathVariable Long orderId, @LoginUser Long userId ){

        shopDeliveryService.deliveryAccept(shopId,orderId,userId);

        return ResponseEntity.ok().build();


    }
    @PatchMapping("/{orderId/cook")
    public ResponseEntity<Void> startCooking (@PathVariable Long shopId,
                                       @PathVariable Long orderId,
                                       @LoginUser Long userId ){

        shopDeliveryService.startCooking(shopId,orderId,userId);

        return ResponseEntity.ok().build();


    }

    @PatchMapping("/{orderId}/depart")
    public ResponseEntity<Void> startDelivering(@PathVariable Long shopId,
                                       @PathVariable Long orderId,
                                       @LoginUser Long userId ){

        shopDeliveryService.startDelivering(shopId,orderId,userId);

        return ResponseEntity.ok().build();


    }

    @GetMapping()
    public ResponseEntity<List<ShopDeliveryResponseDto>> findAllOrder(@PathVariable Long shopId,
                                                                      @LoginUser Long userId ){



        return ResponseEntity.ok().build();


    }
    @GetMapping("/{orderId}")
    public ResponseEntity<ShopDeliveryResponseDto> findOrder(@PathVariable Long shopId,
                                            @PathVariable Long orderId,
                                            @LoginUser Long userId ){

        return ResponseEntity.ok().build();


    }





}
