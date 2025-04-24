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
//    @Cacheable(value = "shopOpenStatus", key = "#shopId", unless = "#result == null", cacheManager = "shopOpenStatusCacheManager")
//    추후 cash 작업 가능 한다면...
    public List<ShopResponseDto> findAllShop(Category category) {
        List<Shop> shops = (category == null)
                ? shopRepository.findAll()
                : shopRepository.findShopByCategory(category);

        return shops.stream()
                .map(shop -> getShopResponseDto(shop.getShopId()))  // 캐시된 DTO를 사용
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
                , shop.getStars()
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
    @Transactional
    @Override
    @Scheduled(cron = "0 0 * * * *") // 매시간 정각
    public void setStarInShop(){
        List<Shop> shops = shopRepository.findAll();
        for (Shop shop : shops) {
            double newStar = menuRepository.calculateAverageStar(shop.getId());
            shop.setStars(newStar);
        }

    }

    public boolean isShopOpen(Long shopId) {
        Shop shop = shopRepository.findByIdThrowException(shopId);
        LocalTime now = LocalTime.now();
        ShopDayOfWeek today = ShopDayOfWeek.of(LocalDate.now().getDayOfWeek().name());

        return !shop.getClosedDays().contains(today) &&
                now.isAfter(shop.getStartTime()) &&
                now.isBefore(shop.getEndTime());
    }



}
