package org.example.outsourcing_project.domain.review.repository;

import org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import org.example.outsourcing_project.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;


import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, Long> {

    @Query("SELECT " +
            "new org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto(r.contents, r.stars, u.name, r.createdAt, r.updatedAt, m.menuName) "+
            "FROM Review r "+
            "JOIN r.order o "+
            "JOIN o.user u "+
            "JOIN o.menu m "+
            "WHERE r.stars BETWEEN :min AND :max ")
    List<ReviewResponseDto> getReviewsByFilter(
            @Param("min") Integer min,
            @Param("max") Integer max,
            Sort sort);
}
