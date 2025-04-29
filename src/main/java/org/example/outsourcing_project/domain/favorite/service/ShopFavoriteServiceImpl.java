package org.example.outsourcing_project.domain.favorite.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.favorite.Dto.FavoriteResponseDto;
import org.example.outsourcing_project.domain.favorite.entity.Favorites;
import org.example.outsourcing_project.domain.favorite.entity.FavoritesId;
import org.example.outsourcing_project.domain.favorite.repository.ShopFavoriteRepository;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ShopFavoriteServiceImpl implements ShopFavoriteService {

    private final ShopFavoriteRepository shopFavoriteRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    public void like(Long userId, Long shopId) {

        Shop shop = shopRepository.findByIdThrowException(shopId);
        User user = userRepository.findByIdOrElseThrow(userId);

        Favorites favorites = Favorites.of(user, shop);

        if (shopFavoriteRepository.existsById(favorites.getId())) {
            throw new RuntimeException("이미 즐겨찾기한 가게입니다.");
        }
        shopFavoriteRepository.save(favorites);
    }

    @Override
    public void unlike(Long userId, Long shopId) {

        FavoritesId favoritesId = new FavoritesId(userId, shopId);

        Favorites favorites = shopFavoriteRepository.findById(favoritesId).
                orElseThrow(() -> new RuntimeException("즐겨찾기에 추가되지 않은 가게입니다."));

        shopFavoriteRepository.delete(favorites);


    }

    @Override
    public List<FavoriteResponseDto> find(Long userId) {

        User user =userRepository.findWithFavoritesById(userId).orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다"));

        return user.getFavorites().stream().
                map(Favorites::getShop)
                .filter(shop -> shop.getShopStatus()!= ShopStatus.CLOSED_PERMANENTLY)
                .map(FavoriteResponseDto::of)
                .toList();
    }


}
