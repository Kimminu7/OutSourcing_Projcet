package org.example.outsourcing_project.domain.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.shop.dto.request.ShopDeleteRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.service.ShopService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shops")
@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping()
    public ResponseEntity<ShopResponseDto> saveShop(@Valid @RequestBody ShopRequestDto requestDto,
                                                    @LoginUser Long userId) {

        return new ResponseEntity<>(shopService.saveShop(userId, requestDto), HttpStatus.CREATED);
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
    public ResponseEntity<ShopResponseDto> patchShop(@PathVariable Long shopId,
                                                     @RequestBody ShopPatchRequestDto shopPatchRequestDto,
                                                     @LoginUser Long userId) {

        return new ResponseEntity<>(shopService.patchShop(userId,shopId, shopPatchRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long shopId,
                                           @LoginUser Long userId,
                                           @RequestBody ShopDeleteRequestDto shopDeleteRequestDto) {

        shopService.deleteShop(shopId,userId,shopDeleteRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

