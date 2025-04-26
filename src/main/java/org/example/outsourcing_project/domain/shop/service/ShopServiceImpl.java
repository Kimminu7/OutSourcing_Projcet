package org.example.outsourcing_project.domain.shop.service;

import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional

    public ShopResponseDto saveShop(Long userid, ShopRequestDto requestDto) {
        User user=userRepository.findById(userid).
                orElseThrow();

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
        shop.updateShopStatus(ShopStatus.CLOSED_PERMANENTLY,ShopStatusAuth.MANUAL);
    }



    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public ShopStatus calculateCurrentStatus(Long shopId, LocalDateTime dateTime) {

        Shop shop=shopRepository.findByIdThrowException(shopId);


        // 고정 상태는 그대로 반환
        if (shop.getShopStatus() == ShopStatus.CLOSED_PERMANENTLY || shop.getShopStatus() == ShopStatus.PENDING) {
            return shop.getShopStatus();
        }

        List<ShopDayOfWeek> closedDays = shop.getClosedDays();
        if (closedDays == null || closedDays.isEmpty()) return ShopStatus.PENDING;

        LocalTime now = dateTime.toLocalTime();
        LocalDate todayDate = dateTime.toLocalDate();

        ShopDayOfWeek today = ShopDayOfWeek.of(todayDate.getDayOfWeek().name());
        ShopDayOfWeek yesterday = ShopDayOfWeek.of(todayDate.minusDays(1).getDayOfWeek().name());

        LocalTime openTime = shop.getOperatingHours().getOpenTime();
        LocalTime closeTime = shop.getOperatingHours().getCloseTime();

        boolean isTodayClosed = closedDays.contains(today);
        boolean isYesterdayClosed = closedDays.contains(yesterday);

        boolean isOpen = false;

        if (openTime.isBefore(closeTime)) {
            if (!isTodayClosed && now.isAfter(openTime) && now.isBefore(closeTime)) {
                isOpen = true;
            }
        } else {
            if ((now.isAfter(openTime) && !isTodayClosed) || (now.isBefore(closeTime) && !isYesterdayClosed)) {
                isOpen = true;
            }
        }

        return isOpen ? ShopStatus.OPEN : ShopStatus.CLOSED;// 구분이 분명해서 삼항 유지
    }



}
