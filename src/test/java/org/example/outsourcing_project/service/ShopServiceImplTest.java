package org.example.outsourcing_project.service;

import org.example.outsourcing_project.common.enums.Category;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;
import org.example.outsourcing_project.common.exception.custom.NOT_FOUND_USER;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.shop.dto.request.ShopRequestDto;
import org.example.outsourcing_project.domain.shop.dto.response.ShopResponseDto;
import org.example.outsourcing_project.domain.shop.entity.Shop;
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

import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        User user=new User("email","password","name","address", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", 1L);
        ShopRequestDto shopRequestDto=new ShopRequestDto("storeName","address","010-1234-4567",0L, LocalTime.now(),LocalTime.now(), Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);
        Shop shop=shopRequestDto.toEntity(user);


    //given : 내가 사용한 mock들은 어떤 값을 줄까?
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(shopRepository.countShopByUserId(1L)).willReturn(1);
        //여기가 mock은 타입만 확인 !!!! 값은 중요X
        given(shopRepository.save(any(Shop.class))).willReturn(shop);
        //when
        ShopResponseDto result=shopService.saveShop(1L,shopRequestDto);


        assertEquals(shop.getAddress(),result.getAddress());

    }

    @Test
    void saveShop_FAIL_cause_over_thre() {
        //given
        User user=new User("email","password","name","address", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", 1L);
        ShopRequestDto shopRequestDto=new ShopRequestDto("storeName","address","010-1234-4567",0L, LocalTime.now(),LocalTime.now(), Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);

        Shop shop=shopRequestDto.toEntity(user);
        ShopResponseDto.from(shop);
        //given : 내가 사용한 mock들은 어떤 값을 줄까?
        given(userRepository.findById(2L)).willReturn(Optional.empty());

        NOT_FOUND_USER exception = assertThrows(NOT_FOUND_USER.class,
                ()->shopService.saveShop(2L,any(ShopRequestDto.class)));

        assertEquals("유저를 찾을 수 없습니다.",exception.getErrorCode().getMessage());

    }
    @Test
    void saveShop_FAIL_cause_not_found_user() {
        //given
        User user=new User("email","password","name","address", UserRole.USER);
        ReflectionTestUtils.setField(user, "userId", 1L);
        ShopRequestDto shopRequestDto=new ShopRequestDto("storeName","address","010-1234-4567",0L, LocalTime.now(),LocalTime.now(), Collections.singletonList(ShopDayOfWeek.FRIDAY), Category.CAFE);

        Shop shop=shopRequestDto.toEntity(user);

        //given : 내가 사용한 mock들은 어떤 값을 줄까?
        given(userRepository.findById(2L)).willReturn(Optional.empty());


        NOT_FOUND_USER exception = assertThrows(NOT_FOUND_USER.class,
                ()->shopService.saveShop(2L,any(ShopRequestDto.class)));

        assertEquals("유저를 찾을 수 없습니다.",exception.getErrorCode().getMessage());

    }

    @Test
    void findAllShop() {



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