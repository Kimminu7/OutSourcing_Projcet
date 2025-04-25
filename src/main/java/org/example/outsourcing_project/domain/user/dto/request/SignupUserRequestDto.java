package org.example.outsourcing_project.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.outsourcing_project.domain.user.UserRole;

@Getter
@AllArgsConstructor
public class SignupUserRequestDto {

    // @Email 은 간단하지만 빈 문자열을 통과 시킨다.(@Pattern을 통한 정규식 검사를 더 많이 사용)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$", message = "비밀번호는 8글자 이상, 영어 대문자 + 특수문자 + 숫자 형식만 가능합니다.")
    private final String password;

    private final String name;

    private final String address;

    private final UserRole role;

}
