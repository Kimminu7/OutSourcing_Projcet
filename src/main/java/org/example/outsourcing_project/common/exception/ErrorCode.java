package org.example.outsourcing_project.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_EMAIL("이메일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-001"),
    NOT_FOUND_PASSWORD("비밀번호를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-002"),
    NOT_FOUND_USER_ID("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-003"),
    NOT_FOUND_POST_ID("게시글를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-004"),
    NOT_FOUND_COMMENT_ID("댓글를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-005"),
    NOT_FOUND_LIKE_ID("좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-006"),

    VALID_ERROR("VAILD가 유효하지 않습니다",HttpStatus.BAD_REQUEST,"400-001");


    private final String message;
    private final HttpStatus httpStatus;
    private final String errorCode;
}
