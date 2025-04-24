package org.example.outsourcing_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.config.PasswordEncoder;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.entity.UserRole;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    @Override
    public UserResponseDto Signup(String email, String password, String name, String address, UserRole role) {

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
                savedUser.getUpdatedAt());
    }

    // 전체 유저 조회
    @Override
    public List<UserResponseDto> findAll() {

        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }

    @Transactional
    @Override
    public UserResponseDto update(Long userId, String oldPassword, String newPassword, String address, UserRole role) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        /**
         * 비밀번호가 암호화 되어 있기 때문에 요청할 oldPasswor와 finduser로 찾은 비밀번호를
         * 비교해주는 matches 메소드를 활용 (트러블 슈팅)
         */
        if(!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new RuntimeException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(newPassword);

        findUser.update(encodePassword, address, role);

        // DB에 실제로 반영하는 역할 (flush()) 중간에 DB에 저장해주는역할
        userRepository.flush();

        return new UserResponseDto(
                findUser.getUserId(),
                findUser.getEmail(),
                findUser.getName(),
                findUser.getAddress(),
                findUser.getRole(),
                findUser.getCreatedAt(),
                findUser.getUpdatedAt()
        );
    }

    // 회원 탈퇴
    @Override
    public void delete(Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        userRepository.delete(findUser);
    }
}
