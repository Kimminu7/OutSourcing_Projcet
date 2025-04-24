package org.example.outsourcing_project.domain.shop.service;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopService {
    ShopResponseDto saveShop(AuthUser authUser, ShopRequestDto requestDto);

    List<ShopResponseDto> findAllShop(Category category);

    ShopWithMenuResponse findShopWithMenu(Long shopId);

    ShopResponseDto patchShop(Long shopId, ShopPatchRequestDto shopPatchRequestDto);

    void deleteShop(Long shopId);
}
