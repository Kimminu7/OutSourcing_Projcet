package org.example.outsourcing_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.entity.UserRole;
import org.example.outsourcing_project.domain.user.repository.UserRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRespository userRespository;

    // 회원 가입
    @Transactional
    @Override
    public UserResponseDto Signup(String email, String password, String name, String address, UserRole role) {

        User addUser = new User(email, password, name, address, role);

        User savedUser = userRespository.save(addUser);

        return new UserResponseDto(savedUser.getUserId(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getName(), savedUser.getAddress(), savedUser.getRole(), savedUser.getCreatedAt(), savedUser.getUpdatedAt());
    }

    // 전체 유저 조회
    @Override
    public List<UserResponseDto> findAll() {

        return userRespository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }
}
