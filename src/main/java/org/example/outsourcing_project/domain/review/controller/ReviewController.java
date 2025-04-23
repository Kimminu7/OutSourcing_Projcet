package org.example.outsourcing_project.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            @SessionAttribute("user") Long userId,
            @PathVariable Long orderId,
            @Valid @RequestBody ReviewRequestDto requestDto) {
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
            @RequestParam(required = false, defaultValue = "newest") String order ){
        List<ReviewResponseDto> result = reviewService.getReviewsByFilter(min, max, order);
        return ResponseEntity.ok(result);
    }

    //@PatchMapping

    //@DeleteMapping
}
