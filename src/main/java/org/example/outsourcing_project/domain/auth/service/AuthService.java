package org.example.outsourcing_project.domain.auth.service;

import jakarta.validation.Valid;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;

public interface AuthService {

    String login(@Valid LoginRequestDto requestDto);
}
