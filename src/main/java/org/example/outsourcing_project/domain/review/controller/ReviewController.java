package org.example.outsourcing_project.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    //@PostMapping

    //@GetMapping

    //@PatchMapping

    //@DeleteMapping
}
