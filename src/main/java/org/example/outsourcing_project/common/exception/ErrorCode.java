package org.example.outsourcing_project.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_CATEGORY("유효하지 않은 카테고리입니다.", HttpStatus.BAD_REQUEST, "400-003"),
    INVALID_DAY("유효하지 않은 요일입니다.", HttpStatus.BAD_REQUEST, "400-003"),
    NOT_FOUND_EMAIL("이메일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-001"),
    NOT_FOUND_PASSWORD("비밀번호를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-002"),
    NOT_FOUND_USER_ID("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-003"),
    NOT_FOUND_POST_ID("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-004"),
    NOT_FOUND_COMMENT_ID("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-005"),
    NOT_FOUND_LIKE_ID("좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-006"),
    NOT_FOUND_ORDER_ID("주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-007"),
    NOT_FOUND_REVIEW_ID("리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-008"),

    VALID_ERROR("VALID 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST, "400-001"),
    INVALID_SORT_ORDER("정렬 키워드가 올바르지 않습니다.", HttpStatus.BAD_REQUEST, "400-002"),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, "400-003"),
    NOT_FOUND_SHOP("가게를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-005"),
  
    UNAUTHORIZED_OWNER("가게 생성 권한이 없습니다",HttpStatus.UNAUTHORIZED,"403-004"),
    FORBIDDEN_OWNER_COUNT("가게 갯수가 너무 많습니다.",HttpStatus.FORBIDDEN,"403-004"),
    FORBIDDEN_OWNER("가게 사장이 아니라서 금지됐습니다.",HttpStatus.UNAUTHORIZED,"403-004"),
    FORBIDDEN_REVIEW("본인의 주문에만 리뷰를 작성할 수 있습니다.", HttpStatus.FORBIDDEN, "403-001"),
    FORBIDDEN_REVIEW_EDIT("본인의 리뷰만 수정할 수 있습니다.", HttpStatus.FORBIDDEN, "403-002"),
    FORBIDDEN_DELETE_REVIEW("본인의 리뷰만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN, "403-003s"),

    // 메뉴
    NOT_FOUND_SHOP("매장을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-009"),
    NOT_FOUND_MENU("메뉴를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-010"),
    DELETED_MENU("삭제된 메뉴는 조회할 수 없습니다.", HttpStatus.BAD_REQUEST, "400-003"),
    FORBIDDEN_ACCESS("권한이 없습니다.", HttpStatus.FORBIDDEN, "403-004"),
    // 메뉴 옵션
    NOT_FOUND_OPTION("옵션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "404-010"),
    INVALID_MENU_OPTION_RELATION("옵션이 해당 메뉴에 속하지 않습니다.", HttpStatus.BAD_REQUEST, "400-004"),
    ALREADY_DELETED_OPTION("이미 삭제된 옵션입니다.", HttpStatus.BAD_REQUEST, "400-005");

    NOT_FOUND_ORDER("주문을 받지 않았습니다.", HttpStatus.NOT_FOUND, "404-009"),
    ORDER_NOT_STARTED("요리를 시작하지 않았습니다.", HttpStatus.BAD_REQUEST, "400-004"),
    ORDER_NOT_CREATED("주문이 생성되지 않았습니다.", HttpStatus.BAD_REQUEST, "400-005"),
    ORDER_NOT_DELIVERED("주문이 배달되지 않았습니다.", HttpStatus.BAD_REQUEST, "400-006"),

    // 새로 추가된 에러
    FORBIDDEN_ORDER_CONFIRM("본인의 주문만 배달 완료 처리할 수 있습니다.", HttpStatus.FORBIDDEN, "403-006");

    private final String message;
    private final HttpStatus httpStatus;
    private final String errorCode;
}
