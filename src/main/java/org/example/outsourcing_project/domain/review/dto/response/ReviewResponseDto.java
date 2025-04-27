package org.example.outsourcing_project.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewResponseDto {
    private final String contents;
    private final Integer stars;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<String> menuNames;
}
