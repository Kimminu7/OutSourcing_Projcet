package org.example.outsourcing_project.domain.auth.service;

import org.example.outsourcing_project.common.config.PasswordEncoder;
import org.example.outsourcing_project.common.jwt.JwtProvider;
import org.example.outsourcing_project.domain.auth.dto.LoginRequestDto;
import org.example.outsourcing_project.domain.user.UserRole;
import org.example.outsourcing_project.domain.user.dto.response.UserResponseDto;
import org.example.outsourcing_project.domain.user.entity.User;
import org.example.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtProvider = mock(JwtProvider.class);
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtProvider);
    }

    // 리플렉션으로 필드 세팅하는 유틸
    private void setField(Object target, String fieldName, Object value) {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // 상위 클래스로 올라가서 계속 찾기
            } catch (IllegalAccessException e) {
                throw new RuntimeException("필드 접근 실패: " + fieldName, e);
            }
        }
        throw new RuntimeException("리플렉션 필드 설정 오류: " + fieldName);
    }


    @Test
    void signup_성공() {
        // given
        String email = "test@example.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        String name = "Tester";
        String address = "Seoul";
        UserRole role = UserRole.USER;

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        User mockUser = new User(email, encodedPassword, name, address, role);
        setField(mockUser, "id", 1L);
        setField(mockUser, "createdAt", LocalDateTime.now());
        setField(mockUser, "updatedAt", LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // when
        UserResponseDto response = authService.Signup(email, rawPassword, name, address, role);

        // then
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getName()).isEqualTo(name);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signup_이미존재하는_이메일() {
        // given
        String email = "test@example.com";
        User existingUser = new User(email, "encodedPassword", "Tester", "Seoul", UserRole.USER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // when & then
        assertThatThrownBy(() ->
                authService.Signup(email, "password", "name", "address", UserRole.USER)
        ).isInstanceOf(RuntimeException.class)
                .hasMessage("이미 가입된 회원입니다.");
    }

    @Test
    void login_성공() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword";
        LoginRequestDto dto = new LoginRequestDto(email, password);

        User mockUser = new User(email, encodedPassword, "Tester", "Seoul", UserRole.USER);
        setField(mockUser, "id", 1L);
        when(userRepository.findByEmailOrElseThrow(email)).thenReturn(mockUser);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtProvider.createToken(mockUser.getId())).thenReturn("mockToken");

        // when
        String token = authService.login(dto);

        // then
        assertThat(token).isEqualTo("mockToken");
    }

    @Test
    void login_비밀번호_불일치() {
        // given
        String email = "test@example.com";
        LoginRequestDto dto = new LoginRequestDto(email, "wrongPassword");

        User mockUser = new User(email, "encodedPassword", "Tester", "Seoul", UserRole.USER);
        when(userRepository.findByEmailOrElseThrow(email)).thenReturn(mockUser);
        when(passwordEncoder.matches(dto.getPassword(), mockUser.getPassword())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
