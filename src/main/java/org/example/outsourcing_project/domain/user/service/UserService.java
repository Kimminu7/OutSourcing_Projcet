package org.example.outsourcing_project.domain.user.service;

import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.entity.UserRole;

import java.util.List;

public interface UserService {

    UserResponseDto Signup(String email, String password, String name, String address, UserRole role);

    List<UserResponseDto> findAll();
}
