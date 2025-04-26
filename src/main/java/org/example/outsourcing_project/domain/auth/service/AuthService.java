package org.example.outsourcing_project.domain.auth.service;

import jakarta.validation.Valid;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto Signup(String email, String password, String name, String address, UserRole role);

    String login(@Valid LoginRequestDto requestDto);
}
