package org.example.outsourcing_project.domain.user.dto.response;

import lombok.Getter;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.entity.UserRole;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long userId;

    private final String email;

    private final String password;

    private final String name;

    private final String address;

    private final UserRole role;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public UserResponseDto(Long userId, String email, String password, String name, String address, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getAddress(),
                user.getRole(),
                user.getCreatedAt(), user.getUpdatedAt());
    }
}
