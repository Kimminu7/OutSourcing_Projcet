package org.example.outsourcing_project.domain.shop.service;


import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;


import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    @Transactional()
    @Override
    public ShopResponseDto saveShop(AuthUser authUser, ShopRequestDto requestDto) {
        User user =User.from(authUser);

        if (userRepository.countShop>=3){
            throw new RuntimeException("부자 사장님");
        }

        Shop shop=new Shop(user,requestDto);
        shopRepository.save(shop);

        return new ShopResponseDto(
                shop.getAddress(),
                shop.getAddress(),
                false,
                shop.getStars(),
                shop.getMinDeliveryPrice());
    }
    @Transactional(readOnly = true)
    @Override
    public List<ShopResponseDto> findAllShop(Category category) {
        // 1. 조건에 따라 shop 목록 조회
        List<Shop> shops = (category == null)
                ? shopRepository.findAll()
                : shopRepository.findShopByCategory(category);

        // 2. ShopId 리스트 뽑기
        List<Long> shopIds = shops.stream()
                .map(Shop::getShopId)
                .toList();

        // 3. 캐시된 isShopOpen 결과를 일괄 조회
        Map<Long, Boolean> openStatusMap = getShopOpenStatusBatch(shopIds);

        // 4. DTO 생성 시 캐시 맵에서 꺼내서 사용
        return shops.stream()
                .map(shop -> new ShopResponseDto(
                        shop.getAddress(),
                        shop.getAddress(),
                        openStatusMap.getOrDefault(shop.getShopId(), false),
                        shop.getStars(),
                        shop.getMinDeliveryPrice()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ShopWithMenuResponse findShopWithMenu(Long shopId) {
        //query문 where + feat join first(쿼리) 작성만 하시면 끝
        List<Menu> menus=  menuRepository.findMenuWithShop(shopId);
        Shop shop=menus.get(0).getShop();
        ShopWithMenuResponse shopWithMenuResponse =new ShopWithMenuResponse(
                shop.getStoreName()
                ,shop.getAddress()
                ,isShopOpen(shopId)
                ,shop.getStars()
                ,shop.getMinDeliveryPrice());
        for (Menu menu: menus){
            ShopWithMenuResponse.MenuItem(menu.getMenuName,menu.getPrice);
        }


        return shopWithMenuResponse;
    }

    @Transactional
    @Override
    public ShopResponseDto patchShop(Long shopId, ShopPatchRequestDto shopPatchRequestDto) {
        Shop shop=shopRepository.findByIdThrowException(shopId);

        return new ShopResponseDto(
                shop.getAddress(),
                shop.getAddress(),
                isShopOpen(shopId),
                shop.getStars(),
                shop.getMinDeliveryPrice());
    }

    @Override
    public void deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
    }


    public Map<Long, Boolean> getShopOpenStatusBatch(List<Long> shopIds) {
        return shopIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        this::isShopOpen
                ));
    }

    @Cacheable(value = "shopOpenStatus", key = "#shopId", unless = "#result == null", cacheManager = "shortTTLCacheManager")
    public boolean isShopOpen(Long shopId) {
        Shop shop = shopRepository.findByIdThrowException(shopId);
        LocalTime now = LocalTime.now();
        ShopDayOfWeek today = ShopDayOfWeek.of(LocalDate.now().getDayOfWeek().name());

        return !shop.getClosedDays().contains(today) &&
                now.isAfter(shop.getStartTime()) &&
                now.isBefore(shop.getEndTime());
    }

}
