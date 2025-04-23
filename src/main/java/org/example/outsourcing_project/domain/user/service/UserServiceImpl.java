package org.example.outsourcing_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.user.dto.SignupUserResponseDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.entity.UserRole;
import org.example.outsourcing_project.domain.user.repository.UserRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRespository userRespository;

    @Transactional
    @Override
    public SignupUserResponseDto Signup(String email, String password, String name, String address, UserRole role) {

        User addUser = new User(email, password, name, address, role);

        User savedUser = userRespository.save(addUser);

        return new SignupUserResponseDto(savedUser.getEmail(), savedUser.getPassword(), savedUser.getName(), savedUser.getAddress(), savedUser.getRole(), savedUser.getCreatedAt(), savedUser.getUpdatedAt());
    }
}
