package org.example.outsourcing_project.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.domain.user.entity.UserRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$",
            message = "비밀번호는 8글자 이상, 영어 대문자 + 특수문자 + 숫자 형식만 가능합니다.")

    private String newPassword;

    @NotBlank(message = "수정할 주소명을 입력해야 합니다.")
    private String address;

    private UserRole role;

}
