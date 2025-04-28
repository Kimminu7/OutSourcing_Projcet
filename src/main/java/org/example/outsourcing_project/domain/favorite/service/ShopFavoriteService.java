package org.example.outsourcing_project.domain.favorite.service;


import org.example.outsourcing_project.domain.favorite.Dto.FavoriteResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ShopFavoriteService{

    void like(Long userId,Long shopId);

    void unlike(Long userId,Long shopId);

    List<FavoriteResponseDto> find(Long userId);

}
