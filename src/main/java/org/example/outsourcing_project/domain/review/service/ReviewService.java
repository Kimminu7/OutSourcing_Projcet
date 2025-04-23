package org.example.outsourcing_project.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
}
