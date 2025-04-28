package org.example.outsourcing_project.domain.shop.controller;


import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shops")
@RestController
@RequiredArgsConstructor
public class ShopStatusController {

    private final ShopStatusController shopStatusController;

    @PatchMapping("/{shopId}/open")
    public ResponseEntity<Void> openShop(
            @LoginUser Long userId,
            @PathVariable Long shopId) {

        shopStatusController.openShop(shopId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{shopId}/close")
    public ResponseEntity<Void> closeShop(
            @LoginUser Long userId,
            @PathVariable Long shopId) {

        shopStatusController.closeShop(shopId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
