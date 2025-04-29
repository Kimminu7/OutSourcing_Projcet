package org.example.outsourcing_project.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.outsourcing_project.common.entity.BaseTimeEntity;
import org.example.outsourcing_project.domain.favorite.entity.Favorites;
import org.example.outsourcing_project.domain.user.UserRole;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    // PK -> 데이터 베이스 규칙에 Pk는 id가 어긋나서 수정했습니다 ^^7
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false)
    private boolean isDeleted = false;


    //즐겨찾기 조회하기 위해서
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorites> favorites = new ArrayList<>();


    public User(String email, String password, String name, String address, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    // 회원 정보 수정시 사용되는 메소드
    public void update(String password, String address, UserRole role) {
        this.password = password;
        this.address = address;
        this.role = role;
    }

    // 회원 탈퇴시 메소드 사용 ( true가 되면 같은 명으로 다시 회원가입 불가능 )
    public void deleteUser() {
        this.isDeleted = true;
    }
}
