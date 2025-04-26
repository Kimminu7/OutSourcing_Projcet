package org.example.outsourcing_project.domain.user.service;

import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.UserRole;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll();

    UserResponseDto update(Long userId, String oldPassword, String newPassword, String address, UserRole role);

    void delete(Long userId, String password);
}
