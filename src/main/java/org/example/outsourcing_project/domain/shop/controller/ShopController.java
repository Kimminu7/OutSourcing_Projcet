package org.example.outsourcing_project.domain.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.service.ShopService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("shops/")
@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping()
    public ResponseEntity<ShopResponseDto> saveShop(@Auth AuthUser authUser,
                                                    @Valid @RequestBody ShopRequestDto requestDto) {
        if (!ObjectUtils.nullSafeEquals(authUser.getUserRole(), UsesRole.ADMIN)) {
            throw new RuntimeException("사장님 왜 이렇게 돈을 많이 넣어주셨어요");
        }
        return new ResponseEntity<>(shopService.saveShop(authUser, requestDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ShopResponseDto>> findAllShop(@RequestParam(required = false) Category category) {
        return new ResponseEntity<>(shopService.findAllShop(category), HttpStatus.OK);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopWithMenuResponse> findShopWithMenu(@PathVariable Long shopId) {
        return new ResponseEntity<>(shopService.findShopWithMenu(shopId), HttpStatus.OK);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopResponseDto> patchShop(@Auth AuthUser authUser,
                                                     @PathVariable Long shopId,
                                                     @RequestBody ShopPatchRequestDto shopPatchRequestDto) {
        if (!ObjectUtils.nullSafeEquals(authUser.getUserRole(), UsesRole.ADMIN)) {
            throw new RuntimeException("사장님 왜 이렇게 돈을 많이 넣어주셨어요");
        }
        if (!ObjectUtils.nullSafeEquals(shopId, authUser.getId())) {
            throw new RuntimeException("사장님이 아니잖아 당신 누구야 당신누구야!!!!!!!");
        }
        return new ResponseEntity<>(shopService.patchShop(shopId, shopPatchRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> deleteShop(@Auth AuthUser authUser,
                                           @PathVariable Long shopId) {
        if (!ObjectUtils.nullSafeEquals(authUser.getUserRole(), UsesRole.ADMIN)) {
            throw new RuntimeException("사장님 왜 이렇게 돈을 많이 넣어주셨어요");
        }
        if (!ObjectUtils.nullSafeEquals(shopId, authUser.getId())) {
            throw new RuntimeException("사장님이 아니잖아 당신 누구야 당신누구야!!!!!!!");
        }
        shopService.deleteShop(shopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



}
