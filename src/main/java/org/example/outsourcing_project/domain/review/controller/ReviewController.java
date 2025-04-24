package org.example.outsourcing_project.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;
import org.example.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ReviewResponseDto> createReview (
            @RequestParam Long userId, //임시 유저ID
            @PathVariable Long orderId,
            @Valid @RequestBody ReviewRequestDto requestDto
    ){

        ReviewResponseDto reviewResponseDto = reviewService.createReview(userId, orderId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    //리뷰 조회
    //최신순 OR 오래된순
    //별점 범위 지정
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByFilter (
            @RequestParam(required = false, defaultValue = "1") int min,
            @RequestParam(required = false, defaultValue = "5") int max,
            @RequestParam(required = false, defaultValue = "desc") String order
    ){
        if(!order.equals("desc") && !order.equals("asc")){
            throw new BaseException(ErrorCode.INVALID_SORT_ORDER);
        }
        List<ReviewResponseDto> result = reviewService.getReviewsByFilter(min, max, order);
        return ResponseEntity.ok(result);
    }

    //리뷰 수정
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> editReview (
            @RequestParam Long userId, //임시 유저ID
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto requestDto
    ){
        ReviewResponseDto result = reviewService.editReview(userId, reviewId, requestDto);
        return ResponseEntity.ok(result);
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview (
            @RequestParam Long userId, //임시 유저ID
            @PathVariable Long reviewId
    ){
        reviewService.deleteReview(userId, reviewId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
