package org.example.outsourcing_project.domain.favorite.controller;


import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.favorite.Dto.FavoriteResponseDto;
import org.example.outsourcing_project.domain.favorite.service.ShopFavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopFavoriteController {
    private final ShopFavoriteService shopFavoriteService;

    @PostMapping("/{shopId}/favorites")
    public ResponseEntity<Void> like(@LoginUser Long id,
                                     @PathVariable Long shopId){

        shopFavoriteService.like(id,shopId);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteResponseDto>> find(@LoginUser Long id){

        return ResponseEntity.ok().body(shopFavoriteService.find(id));
    }

    @DeleteMapping("/{shopId}/favorites")
    public ResponseEntity<Void> unlike(@LoginUser Long id,
                                      @PathVariable Long shopId){

        shopFavoriteService.unlike(id, shopId);

        return ResponseEntity.noContent().build();
    }

}
