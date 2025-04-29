package org.example.outsourcing_project.service;

import org.example.outsourcing_project.common.config.PasswordEncoder;
import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;


import org.example.outsourcing_project.domain.menu.dto.request.MenuCreateRequestDto;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.dto.request.ShopDeleteRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopPatchRequestDto;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopWithMenuResponse;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
import org.example.outsourcing_project.domain.shop.exception.ForbiddenOwnerCount;
import org.example.outsourcing_project.domain.shop.exception.UnauthorizedOwner;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.shop.service.ShopServiceImpl;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {

    @Mock
    private ShopRepository shopRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private ShopServiceImpl shopService;


    @Test
    void saveShop() {
        //given
        User user=new User("email","password","name","address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);
        ShopRequestDto shopRequestDto=new ShopRequestDto("storeName","address","010-1234-4567",0L, LocalTime.now(),LocalTime.now(), Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);
        Shop shop=shopRequestDto.toEntity(user);


    //given : 내가 사용한 mock들은 어떤 값을 줄까?
        given(userRepository.findByIdOrElseThrow(1L)).willReturn(user);
        given(shopRepository.countShopByUserId(1L)).willReturn(1);
        given(shopRepository.save(any(Shop.class))).willReturn(shop);
        //when
        ShopResponseDto result=shopService.saveShop(1L,shopRequestDto);


        assertEquals(shop.getAddress(),result.getAddress());

    }

    @Test
    void saveShop_FAIL_cause_ROle() {
        //when
        User user=new User("email","password","name","address", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", 1L);
        ShopRequestDto shopRequestDto=new ShopRequestDto("storeName","address","010-1234-4567",0L, LocalTime.now(),LocalTime.now(), Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);

        Shop shop=shopRequestDto.toEntity(user);
        ShopResponseDto.from(shop);
        //given : 내가 사용한 mock들은 어떤 값을 줄까?

        given(userRepository.findByIdOrElseThrow(1L)).willReturn(user);
        assertThatThrownBy(() -> shopService.saveShop(1L,shopRequestDto))
                .isInstanceOf(UnauthorizedOwner.class);

    }
    @Test
    void saveShop_FAIL_cause_over_3() {
        //given
        User user=new User("email","password","name","address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);
        ShopRequestDto shopRequestDto=new ShopRequestDto("storeName","address","010-1234-4567",0L, LocalTime.now(),LocalTime.now(), Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);

        Shop shop=shopRequestDto.toEntity(user);
        ShopResponseDto.from(shop);
        //given : 내가 사용한 mock들은 어떤 값을 줄까?

        given(userRepository.findByIdOrElseThrow(1L)).willReturn(user);
        given(shopRepository.countShopByUserId(1L)).willReturn(4);
        assertThatThrownBy(() -> shopService.saveShop(1L,shopRequestDto))
                .isInstanceOf(ForbiddenOwnerCount.class);


    }

    @Test
    void testFindAllShopWithCategory() {
        // given
        User user = new User("email", "password", "name", "address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);

        // ShopRequestDto 설정
        ShopRequestDto shopRequestDto1 = new ShopRequestDto(
                "storeName1", "address1", "010-1234-4567", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);

        ShopRequestDto shopRequestDto2 = new ShopRequestDto(
                "storeName2", "address2", "010-2345-6789", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.MONDAY), Category.CHICKEN);

        ShopRequestDto shopRequestDto3 = new ShopRequestDto(
                "storeName3", "address3", "010-3456-7890", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.SUNDAY), Category.CAFE);

        // Shop 엔티티로 변환
        Shop shop1 = shopRequestDto1.toEntity(user);
        Shop shop2 = shopRequestDto2.toEntity(user);
        Shop shop3 = shopRequestDto3.toEntity(user);

        // ShopStatus 설정
        shop1.updateShopStatus(ShopStatus.OPEN,ShopStatusAuth.MANUAL);  // OPEN 상태
        shop2.setShopStatus(ShopStatus.CLOSED);  // CLOSED 상태
        shop3.setShopStatus(ShopStatus.OPEN);  // OPEN 상태

        // 주어진 카테고리와 상태에 맞는 Shop 객체 반환
        given(shopRepository.findShopByCategory(Category.CAFE))
                .willReturn(Arrays.asList(shop1, shop3));  // Category.CAFE에 해당하는 shop1과 shop3만 반환

        // when
        List<ShopResponseDto> result = shopService.findAllShop(Category.CAFE);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());  // CAFE 카테고리에서 2개의 Shop이 반환되어야 함
        assertEquals("storeName1", result.get(0).getStoreName());
        assertEquals("storeName3", result.get(1).getStoreName());

        // findShopByCategory 호출 확인
        verify(shopRepository, times(1)).findShopByCategory(Category.CAFE);
    }

    @Test
    void findShopWithMenu() {
        // given
        User user = new User("email", "password", "name", "address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);

        ShopRequestDto shopRequestDto1 = new ShopRequestDto(
                "storeName1", "address1", "010-1234-4567", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);

        Shop shop1 = shopRequestDto1.toEntity(user);

        MenuCreateRequestDto menuCreateRequestDto = new MenuCreateRequestDto("name", 123, Category.CHICKEN);
        Menu menu = new Menu(shop1, menuCreateRequestDto);
        shop1.addMenu(menu); // 메뉴를 가게에 추가하는 로직 필요 (만약 없다면)

        shop1.updateShopStatus(ShopStatus.OPEN, ShopStatusAuth.MANUAL);

        List<Menu> menus = shop1.getMenus();
        List<ShopWithMenuResponse.MenuItem> menuItems = menus.stream()
                .map(m -> new ShopWithMenuResponse.MenuItem(m.getName(), m.getPrice()))
                .toList();

        ShopWithMenuResponse expectedResponse = new ShopWithMenuResponse(
                shop1.getShopName(),
                shop1.getAddress(),
                shop1.getShopStatus(),
                0,
                0,
                menuItems
        );

        given(shopRepository.findByIdWithUserThrowException(1L)).willReturn(shop1);

        // when
        ShopWithMenuResponse result = shopService.findShopWithMenu(1L);

        // then
        assertThat(result.getStoreName()).isEqualTo(expectedResponse.getStoreName());
        assertThat(result.getAddress()).isEqualTo(expectedResponse.getAddress());
        assertThat(result.getShopStatus()).isEqualTo(expectedResponse.getShopStatus());
        assertThat(result.getMenus()).hasSize(1);
        assertThat(result.getMenus().get(0).getMenuName()).isEqualTo("name");
        assertThat(result.getMenus().get(0).getPrice()).isEqualTo(123);
    }


    @Test
    void patchShop() {
        // given
        User user = new User("email", "password", "name", "address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);

        ShopRequestDto shopRequestDto1 = new ShopRequestDto(
                "storeName1", "address1", "010-1234-4567", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE
        );

        Shop shop1 = shopRequestDto1.toEntity(user);
        ReflectionTestUtils.setField(shop1, "id", 1L);

        ShopPatchRequestDto shopPatchRequestDto = new ShopPatchRequestDto();
        shopPatchRequestDto.setMinDeliveryPrice(12000L);

        given(shopRepository.findByIdWithUserThrowException(1L)).willReturn(shop1);

        // when
        ShopResponseDto result = shopService.patchShop(1L, 1L, shopPatchRequestDto);

        // then
        assertThat(result.getMinDeliverPrice()).isEqualTo(12000L);
    }

    @Test
    void deleteShop_success_softDelete() {
        // given
        User user = new User("email", "password", "name", "address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);

        ShopRequestDto shopRequestDto1 = new ShopRequestDto(
                "storeName1", "address1", "010-1234-4567", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE
        );

        Shop shop1 = shopRequestDto1.toEntity(user);
        ReflectionTestUtils.setField(shop1, "id", 1L);

        ShopDeleteRequestDto shopDeleteRequestDto = new ShopDeleteRequestDto();
        shopDeleteRequestDto.setPassword("password"); // 비밀번호가 맞음

        given(shopRepository.findActiveShopByIdWithUser(1L)).willReturn(Optional.of(shop1));
        given(passwordEncoder.matches(shopDeleteRequestDto.getPassword(), user.getPassword()))
                .willReturn(true);

        // when
        shopService.deleteShop(1L, 1L, shopDeleteRequestDto); // 정상적인 삭제 요청

        assertEquals(shop1.getId(),1L);
    }

    @Test
    void setStarInShop() {
        // given
        User user = new User("email", "password", "name", "address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);

        ShopRequestDto shopRequestDto = new ShopRequestDto(
                "storeName", "address", "010-1234-5678", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE
        );

        Shop shop = shopRequestDto.toEntity(user);
        ReflectionTestUtils.setField(shop, "id", 1L);

        // 가게 별점 설정
        int newRating = 4; // 별점

        // when
        shop.setStars(newRating); // 가게에 별점을 설정하는 메서드 호출

        // then
        assertEquals(newRating, shop.getStars(), "별점이 올바르게 설정되어야 합니다.");
    }


    @Test
    void isShopOpen() {
        // given
        User user = new User("email", "password", "name", "address", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", 1L);

        ShopRequestDto shopRequestDto = new ShopRequestDto(
                "storeName", "address", "010-1234-5678", 0L,
                LocalTime.now(), LocalTime.now(),
                Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE
        );

        Shop shop = shopRequestDto.toEntity(user);
        ReflectionTestUtils.setField(shop, "id", 1L);
        shop.updateShopStatus(ShopStatus.OPEN,ShopStatusAuth.AUTO);
        // 가게의 상태와 운영시간 설정
        LocalTime openTime = LocalTime.of(9, 0); // 9시 오픈
        LocalTime closeTime = LocalTime.of(18, 0); // 6시 마감
        shop.setOperatingHours(openTime, closeTime);

        given(shopRepository.findActiveShopByIdThrowException(1L)).willReturn(shop);

        LocalDateTime testDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)); // 테스트 시간: 오전 10시

        // when
        ShopStatus status = shopService.calculateCurrentStatus(shop.getId(), testDateTime);

        // then
        assertEquals(ShopStatus.OPEN, status, "가게는 오전 10시에 열려 있어야 합니다.");

        // 테스트 시간 변경 (예: 오후 7시)
//        testDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 0)); // 오후 7시
//        given(shopRepository.findByIdWithUserThrowException(1L)).willReturn(shop);
//        // when
//        status = shopService.calculateCurrentStatus(shop.getId(), testDateTime);
//
//        // then
//        assertEquals(ShopStatus.CLOSED, status, "가게는 오후 7시에 닫혀 있어야 합니다.");
    }

}