package org.example.outsourcing_project.domain.review.service;

import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.menu.entity.Menu;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.entity.Review;
import org.example.outsourcing_project.domain.review.repository.ReviewRepository;
import org.example.outsourcing_project.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.outsourcing_project.common.exception.ErrorCode.NOT_FOUND_ORDER_ID;
import static org.example.outsourcing_project.common.exception.ErrorCode.NOT_FOUND_USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 작성 성공")
    void createReview_success() {
        Long userId = 1L;
        Long orderId = 1L;
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto("내용", 5);

        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(userId);
        when(mockUser.getName()).thenReturn("유저 이름");

        Menu mockMenu = mock(Menu.class);
        when(mockMenu.getName()).thenReturn("메뉴 이름");

        Order mockOrder = mock(Order.class);
        when(mockOrder.getUser()).thenReturn(mockUser);
        when(mockOrder.getStatus()).thenReturn(OrderStatus.DELIVERED);
        when(mockOrder.getMenu()).thenReturn(mockMenu);

        Review mockReview = mock(Review.class);
        when(mockReview.getContents()).thenReturn("내용");
        when(mockReview.getStars()).thenReturn(5);
        when(mockReview.getOrder()).thenReturn(mockOrder);
        when(mockReview.getCreatedAt()).thenReturn(LocalDateTime.now());  // 이 부분 추가

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);

        ReviewResponseDto result = reviewService.createReview(userId, orderId, reviewRequestDto);
        assertThat(result).isNotNull();
        assertThat(result.getContents()).isEqualTo("내용");
        assertThat(result.getStars()).isEqualTo(5);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUserName()).isEqualTo("유저 이름");
        assertThat(result.getMenuName()).isEqualTo("메뉴 이름");
    }

    @Test
    @DisplayName("리뷰 작성 실패 - 주문 ID 없음")
    void createReview_fail_orderNotFound() {
        Long orderId = 1L;
        Long userId = 1L;
        ReviewRequestDto requestDto = new ReviewRequestDto("내용", 5);

        given(orderRepository.findById(orderId)).willReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () -> {
            reviewService.createReview(userId, orderId, requestDto);
        });

        assertThat(exception.getMessage()).isEqualTo("주문을 찾을 수 없습니다.");
        assertThat(exception.getErrorCode()).isEqualTo(NOT_FOUND_ORDER_ID);
    }

    @Test
    @DisplayName("리뷰 작성 실패 - 유저 ID 없음")
    void createReview_fail_userNotFound() {
        Long userId = 1L;
        Long orderId = 1L;

        ReviewRequestDto requestDto = new ReviewRequestDto("내용", 5);

        Order mockOrder = mock(Order.class);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

        BaseException exception = assertThrows(BaseException.class, () -> {
            reviewService.createReview(userId, orderId, requestDto);
        });

        assertThat(exception.getMessage()).isEqualTo("사용자를 찾을 수 없습니다.");
        assertThat(exception.getErrorCode()).isEqualTo(NOT_FOUND_USER_ID);
    }

    @Test
    @DisplayName("리뷰 조회 성공")
    void getReviewsByFilter_success() {
        int min = 1;
        int max = 5;
        String order = "desc";

        ReviewResponseDto mockReviewDto = new ReviewResponseDto("내용", 5, "유저 이름", null, null, "메뉴 이름");
        given(reviewRepository.getReviewsByFilter(min, max, Sort.by("createdAt").descending()))
                .willReturn(List.of(mockReviewDto));

        List<ReviewResponseDto> result = reviewService.getReviewsByFilter(min, max, order);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getContents()).isEqualTo("내용");
        assertThat(result.get(0).getStars()).isEqualTo(5);
        assertThat(result.get(0).getUserName()).isEqualTo("유저 이름");
    }
}

