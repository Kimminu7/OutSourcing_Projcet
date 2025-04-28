package org.example.outsourcing_project.service;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;


import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.entity.Shop;
import org.example.outsourcing_project.domain.shop.enums.ShopStatus;
import org.example.outsourcing_project.domain.shop.enums.ShopStatusAuth;
import org.example.outsourcing_project.domain.shop.exception.ForbiddenOwnerCount;
import org.example.outsourcing_project.domain.shop.exception.UnauthorizedOwner;
import org.example.outsourcing_project.domain.shop.repository.ShopRepository;
import org.example.outsourcing_project.domain.shop.service.ShopService;
import org.example.outsourcing_project.domain.shop.service.ShopServiceImpl;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
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

=======
      


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
    }

    @Test
    void patchShop() {
    }

    @Test
    void deleteShop() {
    }

    @Test
    void setStarInShop() {
    }

    @Test
    void isShopOpen() {
    }
}