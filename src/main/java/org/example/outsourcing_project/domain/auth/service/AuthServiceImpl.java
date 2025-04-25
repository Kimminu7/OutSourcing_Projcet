package org.example.outsourcing_project.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.config.PasswordEncoder;
import org.example.outsourcing_project.common.jwt.JwtProvider;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String login(LoginRequestDto requestDto) {

        User user = userRepository.findByEmailOrElseThrow(requestDto.getEmail());

        // 탈퇴한 회원이면 로그인 불가능
        if(user.isDeleted()) {
            throw new RuntimeException("탈퇴한 회원입니다. 로그인이 불가능합니다.");
        }

        // 비밀번호 일치 여부 검증
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(user.getUserId());
    }
}
