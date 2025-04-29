# 🛵 Outsourcing Project

빠르고 편리한 음식 배달 서비스를 위한 스타트업 아웃소싱 프로젝트입니다.

회원가입부터 가게, 메뉴, 주문, 리뷰 기능까지 실사용을 고려한 배달 플랫폼을 설계하고 구현했습니다.

---

## 📃 API 명세서

<a href="https://www.notion.so/teamsparta/S-A-1dd2dc3ef51480148065d5db9b3040b1"> API 명세서 보러가기 ( 중하단에 위치 ) </a>

---

## 📅 ERD

![아웃소싱 ERD (2)](https://github.com/user-attachments/assets/2d2e2f1b-6aaa-438d-91fb-4ec2bae773a3)

---

## 🛠 기술 스택

| 구분 | 기술 |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| ORM | Spring Data JPA |
| DB | MySQL |
| Build Tool | Gradle |
| 인증 | JWT 기반 인증 (AccessToken 사용) |
| 테스트 | IntelliJ HTTP Client (.http 파일) |
| 기타 | JPA Auditing, Soft Delete, AOP, 커스텀 예외 처리 등 |

---

## 📂 프로젝트 구조

```
org.example.outsourcing_project
├── common                  # 공통 응답 (ApiResponse, ResponseCode 등)
├── config                  # 설정 파일 (JPA Auditing, JWT 등)
├── controller              # API 진입 지점 (User, Shop, Menu, Order, Review)
├── converter               # DTO ↔️ Entity 변환기
├── domain                  # JPA 엔티티
│   ├── user
│   ├── shop
│   ├── menu
│   ├── order
│   ├── review
├── dto                     # 요청/응답 DTO
│   ├── user
│   ├── shop
│   ├── menu
│   ├── order
│   ├── review
├── enums                   # 공통 ENUM 타입 (Category, OrderStatus, DayOfWeek 등)
├── exception               # 공통 및 도메인별 커스텀 예외
│   ├── user / shop / menu / order / review
├── filter                  # JWT 인증 필터
├── jwt                     # JWT 발급 및 검증 유틸리티
├── log                     # 주문 상태 변경 AOP 로깅 기능
├── repository              # JPA 인터페이스
└── service                 # 비즈니스 로직 처리

```

---

## 👥 팀 역할 분담

| 이름 | 담당 |
| --- | --- |
| 김민우 | 사용자(User) 인증/회원가입/로그인 |
| 이소미 | 메뉴(Menu) 메뉴옵션(MenuOption) CRUD 및 가게 내 메뉴 검색 | 
| 임경수  | 가게(Shop) CRUD 및 검색  |
| 정이슬  | 주문(Order) 기능 전체 구현  |
| 장희수 | 리뷰(Review) CRUD 및 별점 필터 조회 |

---

## ✅ 예외 응답 구조 예시

```json
{
  "success": false,
  "data": null,
  "message": "최소 주문 금액을 만족하지 않습니다."
}
```

- 모든 응답은 `ApiResponse<T>` 형태로 일관성 있게 제공됩니다.
- 에러 메시지는 `GlobalExceptionHandler`를 통해 일관되게 관리됩니다.

---

## 📌 추가 예정 기능

- 장바구니 기능
- 포인트 및 쿠폰 시스템
- 가게/메뉴 이미지 업로드 (AWS S3)
- 관리자 대시보드 통계 기능

---

# ✨ THANK YOU ✨

```
빠른 피드백과 높은 완성도를 목표로 한 아웃소싱 프로젝트였습니다.
```
