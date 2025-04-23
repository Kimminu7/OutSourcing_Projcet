package org.example.outsourcing_project.domain.user.service;

import org.example.outsourcing_project.domain.user.dto.SignupUserResponseDto;
import org.example.outsourcing_project.domain.user.entity.UserRole;

public interface UserService {

    SignupUserResponseDto Signup(String email, String password, String name, String address, UserRole role);
}
