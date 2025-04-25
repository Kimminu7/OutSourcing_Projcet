package org.example.outsourcing_project.domain.shop.controller;


import lombok.RequiredArgsConstructor;
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
            @PathVariable Long shopId) {
//        (@Auth AuthUser authUser,
//        if (!ObjectUtils.nullSafeEquals(authUser.getUserRole(), UsesRole.ADMIN)) {
//            throw new RuntimeException("사장님 왜 이렇게 돈을 많이 넣어주셨어요");
//        }
//        if (!ObjectUtils.nullSafeEquals(shopId, authUser.getId())) {
//            throw new RuntimeException("사장님이 아니잖아 당신 누구야 당신누구야!!!!!!!");
//        }
        shopStatusController.openShop(shopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{shopId}/close")
    public ResponseEntity<Void> closeShop(
            @PathVariable Long shopId) {
//        (@Auth AuthUser authUser,
//        if (!ObjectUtils.nullSafeEquals(authUser.getUserRole(), UsesRole.ADMIN)) {
//            throw new RuntimeException("사장님 왜 이렇게 돈을 많이 넣어주셨어요");
//        }
//        if (!ObjectUtils.nullSafeEquals(shopId, authUser.getId())) {
//            throw new RuntimeException("사장님이 아니잖아 당신 누구야 당신누구야!!!!!!!");
//        }
        shopStatusController.closeShop(shopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
