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
            "new org.example.outsourcing_project.domain.review.dto.response.ReviewResponseDto(r.contents, r.stars, u.name, m.menu_name, r.createdAt, r.updatedAt) "+
            "FROM Review r "+
            "JOIN r.order o "+
            "JOIN User u ON u.id = o.userId " +
            "JOIN Menu m ON m.id = o.menuId " +
            "WHERE r.stars BETWEEN :min AND :max ")
    List<ReviewResponseDto> getReviewsByFilter(
            @Param("min") Integer min,
            @Param("max") Integer max,
            Sort sort);
}
