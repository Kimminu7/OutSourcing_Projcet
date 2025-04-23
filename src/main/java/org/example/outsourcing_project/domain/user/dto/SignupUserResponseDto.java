package org.example.outsourcing_project.domain.user.dto;

import lombok.Getter;
import org.example.outsourcing_project.domain.user.entity.UserRole;

import java.time.LocalDateTime;

@Getter
public class SignupUserResponseDto {

    private final String email;

    private final String password;

    private final String name;

    private final String address;

    private final UserRole role;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public SignupUserResponseDto(String email, String password, String name, String address, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
