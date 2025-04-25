package org.example.outsourcing_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.outsourcing_project.common.config.PasswordEncoder;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        // 수정할 유저를 조회
        User findUser = userRepository.findByIdOrElseThrow(userId);

        /**
         * 비밀번호가 암호화 되어 있기 때문에 요청할 oldPasswor와 finduser로 찾은 비밀번호를
         * 비교해주는 matches 메소드를 활용 (트러블 슈팅)
         */
        if(!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
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
    @Transactional
    @Override
    public void delete(Long userId, String password) {
        // 삭제할 유저를 조회
        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 1. 이미 탈퇴한 회원일 경우
        if(findUser.isDeleted()) {
            throw new RuntimeException("이미 탈퇴한 사용자 입니다.");
        }

        // 2. 비밀번호 일치 여부 검증
        if(!passwordEncoder.matches(password, findUser.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 탈퇴 처리 여부
        findUser.deleteUser();
    }
}
