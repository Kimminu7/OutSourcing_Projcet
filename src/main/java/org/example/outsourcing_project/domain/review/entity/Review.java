package org.example.outsourcing_project.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.common.entity.BaseTimeEntity;
import org.example.outsourcing_project.domain.order.entity.Order;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name="contents", nullable = true, length = 255)
    private String contents;

    @Column(name="stars", nullable = false)
    private Integer stars;

    public Review(Order order, String contents, int stars) {
        this.order = order;
        this.contents = contents;
        this.stars = stars;
    }

    //별점 업데이트
    public void updateStars(Integer stars) {
        this.stars = stars;
    }

    //리뷰 내용 업데이트
    public void updateContents(String contents) {
        this.contents = contents;
    }
}
