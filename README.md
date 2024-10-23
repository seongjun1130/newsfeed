## 프로젝트명: NewsFeed 프로젝트

---

### 프로젝트 개요
NewsFedd 프로젝트는 사용자들이 게시물을 작성하고 조회할 수 있는 간단한 뉴스피드 기능을 제공하는 웹 어플리케이션입니다. 사용자는 뉴스 게시물을 작성, 수정, 삭제할 수 있으며, 다른 사용자들의 기수물과 댓글을 볼 수 있습니다. 또한, 기간별로 뉴스피드를 조회할 수 있는 기능을 지원합니다.

---

### **프로젝트 구성 및 주요 기능**

1. **회원 가입**
    - 회원 가입 (POST `/api/member/signup`)
  
   1-1 **로그인**
    - 로그인 (POST `/api/member/login`)
    - JWT 토큰을 통한 인증 및 권한 부여
  
   1-2 **프로필 수정**
    - 제공된 정보에 한 해서 자신의 프로필을 수정할 수 있습니다.
  
   1-3 **본인 및 타인 프로필 조회**
    - 자신과 타인의 프로필 정보를 조회할 수 있습니다.

2. **뉴스 피드 관리**
    - 게시물 작성 (POST `/api/news`)
    - 게시물 전체 조회 (GET `/api/news`)
    - 게시물 단건 조회 (GET `/api/news/{id}`)
    - 게시물 수정 (PUT `/api/news/{id}`)
    - 게시물 삭제 (DELETE `/api/news/{id}`)

3. **친구 관리**
    - 친구 요청 (POST `/api/member/friends/{id}`)
    - 친구 수락 (POST `/api/member/friends`)
    - 친구 삭제 (DELETE `/api/member/friends/{id}`)
    - 친구 목록 조회 (GET `/api/member/friends`)

4. **댓글 기능**
    - 댓글 작성 (POST `/api/news/{id}/comment`)
    - 댓글 수정 (PUT `/api/news/{id}/comment`)
    - 댓글 삭제 (DELETE `/api/news/{newsId}/{commentId}`)

---

### **빌드 및 실행 방법**

#### 1. **필수 설치 사항**
- JDK 17
- MySQL 데이터베이스
- Gradle 빌드 툴

#### 2. **설정 파일 (application.yml)**

- `src/main/resources/application.properties`에 데이터베이스 설정 및 JWT 시크릿 키를 설정합니다.

```
spring.application.name=NewsFeedProject
spring.datasource.url=jdbc:mysql://localhost:3306/newsfeeds
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
jwt.secret.key= "your own key"
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicTypeDescriptor=TRACE
```


#### 3. **빌드 및 실행 명령**

프로젝트 빌드를 위해 `Gradle`을 사용합니다. 다음 명령어를 사용하여 프로젝트를 빌드 및 실행하세요.

```bash
./gradlew clean build
./gradlew bootRun
```

---

### **의존성 관리 (build.gradle)**

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.mysql:mysql-connector-j'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

    // Validation
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    // PasswordEncoder
    implementation 'at.favre.lib:bcrypt:0.10.2'

    // Mapper (MapStruct)
    implementation "org.mapstruct:mapstruct:1.5.2.Final"
    annotationProcessor "org.mapstruct:mapstruct-processor:1.5.2.Final"
    testAnnotationProcessor "org.mapstruct:mapstruct-processor:1.5.2.Final"
    implementation 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
    annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'

    // JWT
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
    implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'
}
```

---

### **주요 기술 스택**

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **Template Engine**: Thymeleaf
- **Validation**: Spring Boot Validation
- **Password Encryption**: Bcrypt
- **Mapping**: MapStruct
- **Testing**: JUnit, Spring Security Test

---

### API 명세서 표

| 담당      | 진행상황 | Method | 기능   | URL                                     | Request Example                                                                                                      | Response Example                                                                                                 | Request Header                   | Response Header                   | 상태 코드                    |
|-----------|----------|--------|--------|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|-----------------------------------|-----------------------------------|------------------------------|
| 조성준    | 진행 중   | POST   | 로그인 | /api/member/login                       | `{ "Email" : "abc@naver.com", "password" : "5Wh5djr!" }`                                                             | `{ "token": "————", "nickname" : "5조5억", "memberId" : 1 }`                                                      | **Content-Type: application/json**|                                   | 200 OK                       |
| 조성준    | 완료      | POST   | 회원가입| /api/member/signup                      | `{ "email" : "abc@naver.com", "nickname" : "5조5억", "password" : "5Wh5djr!", "phoneNumber" : "01012341111", "country" : "대한민국" }`| `{ "nickname" : "5조5억", "memberId" : 1 }`                                                                        | **Content-Type: application/json**|                                   | 204(가입 성공), 409(중복), 400(누락) |
| 조성준    |           | UPDATE | 회원탈퇴 | /api/member/profil                      | N/A                                                                                                                  | N/A                                                                                                              |                                   |                                   |                              |
| 소성      |           | GET    | 본인 프로필 조회 | /api/member/profil                   | N/A                                                                                                                  | `{ "email" : "abc@naver.com", "nickname" : "5조5억", "call" : "01012341111", "country" : "대한민국" }`              |                                   |                                   | 200 OK                       |
| 소성      |           | GET    | 타인 프로필 조회 | /api/member/profil/{id}             | `{id} : targetId`                                                                                                    | `{ "email" : "abc@naver.com", "nickname" : "4조4억", "country" : "미국" }`                                         |                                   |                                   | 200 OK                       |
| 소성      |           | UPDATE | 프로필 수정   | /api/member/profil                      | `{ "nickname" : "키키키", "password" : "dkjsdj!dS1", "call" : "01011112222", "country" : "미국" }`                   | `{ "nickname" : "5조5억" }`                                                                                       |                                   |                                   | 200 OK                       |
| 박상원    |           | POST   | 게시물 작성   | /api/news                               | `{ "title" : "첫 번째 게시글", "content" : "내용입니다!!" }`                                                         | `{ "newsId" : 1 }`                                                                                               |                                   |                                   | 201 Created                  |
| 박상원    |           | GET    | 게시물 전체 조회 | /api/news?pageNo=1&pageSize=10      | N/A                                                                                                                  | `[ { "title" : "제목입니다", "content" : "내용입니다", "nickname" : "5조5억", "commentCount" : "20", "modifyAt" : "YY-MM-DD" } ... ]`|                                   |                                   | 200 OK                       |
| 박상원    |           | GET    | 게시물 단건 조회 | /api/news/{id}                      | `{id} : newsId`                                                                                                      | `{ "title" : "제목입니다", "content" : "내용입니다", "nickname" : "5조5억", "modifyAt" : "YY-MM-DD", "commentList" : [ ... ] }` |                                   |                                   | 200 OK                       |
| 박상원    |           | UPDATE | 게시물 수정   | /api/news/{id}                          | `{ "title" : "첫 번째 게시글 수정", "content" : "수정 내용입니다!!" }`                                               | `{ "newsId" : 1 }`                                                                                               |                                   |                                   | 200 OK                       |
| 박상원    |           | DELETE | 게시물 삭제   | /api/news/{id}                          | `{id} : newsId`                                                                                                      | N/A                                                                                                              |                                   |                                   | 204 No Content               |
| 김명훈    |           | GET    | 친구 조회    | /api/member/friends                     | N/A                                                                                                                  | `{ "email" : "abc@naver.com", "nickname" : "4조4억", "country" : "미국" }`                                        |                                   |                                   | 200 OK                       |
| 김명훈    |           | POST   | 친구 신청   | /api/member/friends/{id}                | `{id} : targetMemberId`, `{ "targetNickname" : "3조3억" }`                                                           | N/A                                                                                                              |                                   |                                   | 201 Created                  |
| 김명훈    |           | DELETE | 친구 삭제   | /api/member/friends/{id}                | `{id} : appliedMemberId`, `{ "targetNickname" : "3조3억" }`                                                          | N/A                                                                                                              |                                   |                                   | 204 No Content               |
| 김명훈    |           | POST   | 친구 수락   | /api/member/friends                     | `{ "appliedMemberId" : 11, "status" : "true" }`                                                                      | `{ "message" : "친구 요청을 수락했습니다." }`                                                                     |                                   |                                   | 200 OK                       |
| 김명훈    |           | DELETE | 친구 거절   | /api/member/friends                     | `{ "appliedMemberId" : 11, "status" : "false" }`                                                                     | `{ "message" : "친구 요청을 거절했습니다." }`                                                                     |                                   |                                   | 200 OK                       |
| 김명훈    |           | POST   | 좋아요 누르기  | /api/news/{id}                          | `{id} : newsId`, `{ "newsId" : 1 }`                                                                                  | N/A                                                                                                              |                                   |                                   | 200 OK                       |
| 김명훈    |           | DELETE | 좋아요 취소  | /api/news/{id}                          | `{id} : newsId`, `{ "newsId" : 1 }`                                                                                  | N/A                                                                                                              |                                   |                                   | 204 No Content               |
| 박인선    |           | POST   | 댓글 작성   | /api/news/{id}/comment                  | `{ "comment" : "댓글 입니다." }`                                                                                     | `{ "commentId" : 1 }`                                                                                             |                                   |                                   | 201 Created                  |
| 박인선    |           | UPDATE | 댓글 수정   | /api/news/{id}/comment                  | `{ "comment" : "댓글 수정 입니다." }`                                                                                | `{ "commentId" : 1 }`                                                                                             |                                   |                                   | 200 OK                       |
| 박인선    |           | DELETE | 댓글 삭제   | /api/news/{newsId}/{commentId}           | N/A                                                                                                                  | N/A                                                                                                              |                                   | 204 No Content               |


---

### **기타 사항**

- **데이터베이스 마이그레이션**: `src/main/resources/application.properties` 파일에서 데이터베이스 설정을 맞춘 후 MySQL 데이터베이스를 준비해주세요.
- **테스트**: 모든 API는 `Postman`을 통해 테스트할 수 있습니다.
