package org.example.outsourcing_project.domain.shop.service;

import jakarta.persistence.LockModeType;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ShopService {
    ShopResponseDto saveShop(Long userid, ShopRequestDto requestDto);

    List<ShopResponseDto> findAllShop(Category category);

    ShopWithMenuResponse findShopWithMenu(Long shopId);

    ShopResponseDto patchShop(Long shopId, ShopPatchRequestDto shopPatchRequestDto);

    void deleteShop(Long shopId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ShopStatus calculateCurrentStatus(Long shopId, LocalDateTime dateTime);
}
