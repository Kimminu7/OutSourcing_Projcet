package org.example.outsourcing_project.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.user.dto.SignupUserRequestDto;
import org.example.outsourcing_project.domain.user.dto.SignupUserResponseDto;
import org.example.outsourcing_project.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponseDto> Signup(@Valid @RequestBody SignupUserRequestDto requestDto) {

        SignupUserResponseDto responseDto = userService.Signup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getName(), requestDto.getAddress(), requestDto.getRole());

        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }
}
