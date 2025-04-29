package org.example.outsourcing_project.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.UserRole;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private final Long userId;

    private final String email;

    private final String name;

    private final String address;

    private final UserRole role;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getAddress(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
