package org.example.outsourcing_project.domain.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.exception.custom.NOT_FOUND_USER;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional

    public ShopResponseDto saveShop(Long userid, ShopRequestDto requestDto) {
        User user=userRepository.findById(userid).
                orElseThrow(NOT_FOUND_USER::new);

        if (shopRepository.countShopByUserId(userid)>=3){
            throw new RuntimeException("부자 사장님");
        }
        Shop shop=requestDto.toEntity(user);

        Shop saveshop=shopRepository.save(shop);

        return ShopResponseDto.from(saveshop);
    }


    @Transactional(readOnly = true)
    @Override
//    @Cacheable(value = "shopOpenStatus", key = "#shopId", unless = "#result == null", cacheManager = "shopOpenStatusCacheManager")
//    추후 cash 작업 가능 한다면...
    public List<ShopResponseDto> findAllShop(Category category) {
        List<Shop> shops = (category == null)
                ? shopRepository.findAll()
                : shopRepository.findShopByCategory(category);

        return shops.stream()
                .map(ShopResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ShopWithMenuResponse findShopWithMenu(Long shopId) {
        Shop shop=shopRepository.findByIdThrowException(shopId);
        List<Menu> menus=shop.getMenus();
        List<ShopWithMenuResponse.MenuItem> menuItems = menus.stream()
                .map(menu -> new ShopWithMenuResponse.MenuItem(menu.getName(), menu.getPrice()))
                .collect(Collectors.toList());

        return ShopWithMenuResponse.from(shop, menuItems);
    }

    @Transactional
    @Override
    public ShopResponseDto patchShop(Long shopId, ShopPatchRequestDto shopPatchRequestDto) {
        Shop shop=shopRepository.findByIdThrowException(shopId);
        shop.update(shopPatchRequestDto);
        return ShopResponseDto.from(shop);
    }

    @Override
    public void deleteShop(Long shopId) {
        Shop shop=shopRepository.findByIdThrowException(shopId);
        shop.updateShopStatus(ShopStatus.CLOSED_PERMANENTLY);
    }
}
