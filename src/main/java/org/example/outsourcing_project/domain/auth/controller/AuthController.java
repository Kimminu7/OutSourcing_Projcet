package org.example.outsourcing_project.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;
import org.example.outsourcing_project.domain.auth.dto.LoginResponseDto;
import org.example.outsourcing_project.domain.auth.service.AuthService;
import org.example.outsourcing_project.domain.auth.dto.SignupRequestDto;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> Signup(@Valid @RequestBody SignupRequestDto requestDto) {

        UserResponseDto responseDto = authService.Signup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getName(), requestDto.getAddress(), requestDto.getRole());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {

        String token = authService.login(requestDto);

        return new ResponseEntity<>(new LoginResponseDto(token),HttpStatus.OK);
    }


}
