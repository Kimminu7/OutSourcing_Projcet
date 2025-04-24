package org.example.outsourcing_project.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    private final String email;
    private final String password;
}
