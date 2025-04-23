package org.example.outsourcing_project.domain.review.repository;

import org.example.outsourcing_project.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository <Review, Long> {
}
