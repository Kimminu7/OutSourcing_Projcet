package org.example.outsourcing_project.domain.auth.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;
import org.example.outsourcing_project.domain.auth.dto.LoginResponseDto;
import org.example.outsourcing_project.domain.auth.service.AuthService;
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

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {

        String token = authService.login(requestDto);

        return new ResponseEntity<>(new LoginResponseDto(token),HttpStatus.OK);
    }
}
