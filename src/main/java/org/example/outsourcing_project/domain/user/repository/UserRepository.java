package org.example.outsourcing_project.domain.user.repository;

import org.example.outsourcing_project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // DB에 저장되어 있지 않은 userId 예외처리
    default User findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 userId = " + userId));
    }
    // 유저 이메일(아이디 값) 찾기
    Optional<User> findByEmail(String email);
}
