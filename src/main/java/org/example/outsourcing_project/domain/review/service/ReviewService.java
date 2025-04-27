package org.example.outsourcing_project.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.order.entity.Order;
import org.example.outsourcing_project.domain.order.entity.OrderStatus;
import org.example.outsourcing_project.domain.order.repository.OrderRepository;
import org.example.outsourcing_project.domain.menu.repository.MenuRepository;
import org.example.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.entity.Review;
import org.example.outsourcing_project.domain.review.repository.ReviewRepository;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    //리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long currentUserId, Long orderId, ReviewRequestDto requestDto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER_ID));

        if (order.getUser() == null) {
            throw new BaseException(ErrorCode.NOT_FOUND_USER_ID);
        }
        User user = order.getUser();

        //현재 로그인된 유저와 주문의 유저가 같다면 리뷰 저장
        if(currentUserId.equals(user.getId()) && order.getStatus() == OrderStatus.DELIVERED){
            Review review = new Review(order, requestDto.getContents(), requestDto.getStars());
            Review savedReview = reviewRepository.save(review);

            return buildReviewResponseDto(savedReview, user, order);
        }
        throw new BaseException(ErrorCode.FORBIDDEN_REVIEW);
    }

    //리뷰 조회
    public List<ReviewResponseDto> getReviewsByFilter(int min, int max, String order) {
        List<ReviewResponseDto> listResponseDto = new ArrayList<>();

        if(order.equals("desc")){
            listResponseDto = reviewRepository.getReviewsByFilter(min, max, Sort.by("createdAt").descending());
        } else if (order.equals("asc")){
            listResponseDto = reviewRepository.getReviewsByFilter(min, max, Sort.by("createdAt").ascending());
        }

        return listResponseDto;
    }

    //리뷰 수정
    @Transactional
    public ReviewResponseDto editReview(Long currentUserId, Long reviewId, ReviewRequestDto requestDto){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_REVIEW_ID));

        Order order = review.getOrder();
        if (order.getUser() == null) {
            throw new BaseException(ErrorCode.NOT_FOUND_USER_ID);
        }
        User user = order.getUser();

        if(currentUserId.equals(user.getId())){
            if(requestDto.getStars() != null){
                review.updateStars(requestDto.getStars());
            }
            if(requestDto.getContents() != null){
                review.updateContents(requestDto.getContents());
            }
            return buildReviewResponseDto(review, user, order);
        }
        throw new BaseException(ErrorCode.FORBIDDEN_REVIEW_EDIT);
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(Long currentUserId, Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_REVIEW_ID));

        if(currentUserId.equals(review.getOrder().getUser().getId())){
            reviewRepository.delete(review);
        } else {
            throw new BaseException(ErrorCode.FORBIDDEN_DELETE_REVIEW);
        }
    }

    private ReviewResponseDto buildReviewResponseDto(Review review, User user, Order order) {

        List<String> menuNames = order.getOrderMenus().stream()
                .map(orderMenu -> menuRepository.findById(orderMenu.getMenuId())
                        .map(menu -> menu.getName())
                        .orElse("Unknown Menu"))
                .collect(Collectors.toList());

        return ReviewResponseDto.builder()
                .contents(review.getContents())
                .stars(review.getStars())
                .userName(user.getName())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .menuNames(menuNames)
                .build();
    }
}
