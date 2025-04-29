package org.example.outsourcing_project.domain.shop.controller;


import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.shop.service.ShopService;
import org.example.outsourcing_project.domain.shop.service.ShopStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shops")
@RestController
@RequiredArgsConstructor
public class ShopStatusController {

    private final ShopStatusService shopStatusService;

    @PatchMapping("/{shopId}/open")
    public ResponseEntity<Void> openShop(
            @LoginUser Long userId,
            @PathVariable Long shopId) {

        shopStatusService.openShop(shopId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{shopId}/close")
    public ResponseEntity<Void> closeShop(
            @LoginUser Long userId,
            @PathVariable Long shopId) {

        shopStatusService.closeShop(shopId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
