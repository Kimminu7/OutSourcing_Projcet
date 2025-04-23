package org.example.outsourcing_project.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.entity.Review;
import org.example.outsourcing_project.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    //리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long currentUserId, Long orderId, ReviewRequestDto requestDto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER_ID));

        //현재 로그인된 유저와 주문의 유저가 같다면 리뷰 저장
        if(currentUserId.equals(order.getUser().getUserId())){
            Review review = new Review(order, requestDto.getContents(), requestDto.getStars());
            Review savedReview = reviewRepository.save(review);

            return ReviewResponseDto.builder()
                    .contents(savedReview.getContents())
                    .stars(savedReview.getStars())
                    .userName(order.getUser().getName())
                    .createdAt(savedReview.getCreatedAt())
                    .updatedAt(savedReview.getUpdatedAt())
                    .orderMenu(order.getMenu().getMenuName())
                    .build();
        }
        throw new BaseException(ErrorCode.UNAUTHORIZED_REVIEW);
    }

    //리뷰 조회
    public List<ReviewResponseDto> getReviewsByFilter(int min, int max, String order) {

    }

    //리뷰 수정
    public ReviewResponseDto editReview(Long currentUserId, Long reviewId, ReviewRequestDto requestDto){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_REVIEW_ID));

        Order order = review.getOrder();
        User user = order.getUser();

        if(currentUserId.equals(user.getUserId())){
            if(requestDto.getStars() != null){
                review.updateStars(requestDto.getStars());
            }
            if(requestDto.getContents() != null){
                review.updateContents(requestDto.getContents());
            }
            return ReviewResponseDto.builder()
                    .contents(review.getContents())
                    .stars(review.getStars())
                    .userName(user.getName())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .orderMenu(order.getMenu().getMenuName())
                    .build();
        }
        throw new BaseException(ErrorCode.UNAUTHORIZED_EDIT);
    }

    //리뷰 삭제
}
