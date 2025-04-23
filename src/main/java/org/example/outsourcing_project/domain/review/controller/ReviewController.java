package org.example.outsourcing_project.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ReviewResponseDto> createReview (
            @PathVariable Long orderId,
            @Valid @RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(orderId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    //@GetMapping

    //@PatchMapping

    //@DeleteMapping
}
