package org.example.outsourcing_project.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.domain.user.dto.request.SignupUserRequestDto;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> Signup(@Valid @RequestBody SignupUserRequestDto requestDto) {

        UserResponseDto responseDto = userService.Signup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getName(), requestDto.getAddress(), requestDto.getRole());

        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    // 전체 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<UserResponseDto> responseDtoList = userService.findAll();

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }
}
