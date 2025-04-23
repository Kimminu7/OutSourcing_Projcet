package org.example.outsourcing_project.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.common.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이름
    @Column(nullable = false)
    private String name;

    // 주소
    @Column(nullable = false)
    private String address;

    // 역할/권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public User(String email, String password, String name, String address, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }

}
