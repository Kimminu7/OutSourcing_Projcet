package org.example.outsourcing_project.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.jwt.LoginUser;
import org.example.outsourcing_project.domain.user.dto.request.DeleteUserRequestDto;
import org.example.outsourcing_project.domain.user.dto.request.UpdateUserRequestDto;
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

    // 전체 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<UserResponseDto> responseDtoList = userService.findAll();

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    // 회원 정보 수정
    @PatchMapping
    public ResponseEntity<UserResponseDto> update(
            @LoginUser Long userId,
            @RequestBody UpdateUserRequestDto requestDto
    ) {

        UserResponseDto responseDto = userService.update(userId, requestDto.getOldPassword(), requestDto.getNewPassword(), requestDto.getAddress(), requestDto.getRole());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @LoginUser Long userId,
            @RequestBody DeleteUserRequestDto requestDto
    ) {

        userService.delete(userId, requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
