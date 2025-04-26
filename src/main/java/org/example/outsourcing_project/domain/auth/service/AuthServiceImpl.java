package org.example.outsourcing_project.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.config.PasswordEncoder;
import org.example.outsourcing_project.common.jwt.JwtProvider;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 회원 가입
    @Transactional
    @Override
    public UserResponseDto Signup(String email, String password, String name, String address, UserRole role) {

        // 기존 유저 존재 여부 확인
        Optional<User> findUserEmail = userRepository.findByEmail(email);

        if(findUserEmail.isPresent()) {
            User existUser = findUserEmail.get(); // Optional get() 사용하면 객체에 접근가능함.
            // 이미 탈퇴한 사용자 이면 재가입 불가능
            if(existUser.isDeleted()) {
                throw new RuntimeException("이미 탈퇴한 회원입니다. 재가입이 불가능합니다.");
            }
            throw new RuntimeException("이미 가입된 회원입니다.");
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);

        User addUser = new User(email, encodePassword, name, address, role);

        User savedUser = userRepository.save(addUser);

        return new UserResponseDto(
                savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getAddress(),
                savedUser.getRole(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }

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
