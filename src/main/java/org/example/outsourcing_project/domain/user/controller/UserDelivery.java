package org.example.outsourcing_project.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.user.service.UserDeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/orders")
@RequiredArgsConstructor
public class UserDelivery {

    private final UserDeliveryService userDeliveryService;

    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<Void> confirm (@PathVariable Long orderId,
                                              @LoginUser Long userId ){

        userDeliveryService.confirm(orderId,userId);

        return ResponseEntity.ok().build();


    }
    @PatchMapping("")
    public ResponseEntity<Void> startCooking (@LoginUser Long userId ){

        userDeliveryService.findAll(userId );

        return ResponseEntity.ok().build();


    }
}
