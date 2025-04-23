package org.example.outsourcing_project.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.entity.Review;
import org.example.outsourcing_project.domain.review.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));

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

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 주문에만 리뷰를 작성할 수 있습니다.");
    }

    //리뷰 조회
    public List<ReviewResponseDto> getReviewsByFilter(int min, int max, String order) {

    }
}
