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
    - 본인 프로필 수정(PUT `/api/member/profil`)
  
   1-3 **본인 및 타인 프로필 조회**
    - 자신과 타인의 프로필 정보를 조회할 수 있습니다.
    - 타인 프로필 조회(GET `/api/member/profil/{id}`)
    - 본인 프로필 조회(GET `/api/member/profil`)

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

아래와 같이 표로 정리해드렸습니다.

| 담당    | 진행상황 | method   | 기능                | URL                                     | request                                                                                                    | response                                                                                                  | request header                                                       | response header                                           | 상태코드                                              |
|---------|----------|----------|---------------------|-----------------------------------------|-----------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------|----------------------------------------------------------|----------------------------------------------------------|
| 조성준  | 완료     | POST     | 로그인              | /api/member/login                       | { ”Email” : “abc@naver.com”, ”password” : “5Wh5djr!” }                                                     | { ”memberId” : 1, ”message” : “로그인 되었습니다.”, ”token” : "Bearer ————” }                               | **Content-Type: application/json**                                    | **Authorization: Bearer {token}, Content-Type: application/json**      | 200(로그인 성공), 400(토큰 누락), 401(미유효토큰), 404(탈퇴한 유저)   |
| 조성준  | 완료     | POST     | 회원가입            | /api/member/signup                      | { ”email” : “abc@naver.com”, ”nickname” : “5조5억”, ”password” : “5Wh5djr!”, ”country” : “South Korea” }    | { ”memberId” : 1, ”message” : “회원가입 되었습니다.” }                                                    | **Content-Type: application/json**                                    | **Content-Type: application/json**                                    | 201(가입 성공), 400(필수값 누락), 409(필드값 중복)                   |
| 조성준  | 완료     | DELETE   | 회원탈퇴            | /api/member/profil                      | { ”password” : “5Wh5djr!” }                                                                                | { ”memberId” : 1, ”message” : “회원탈퇴 되었습니다.” }                                                    | **Authorization: Bearer {token}, Content-Type: application/json**      | **Content-Type: application/json**                                    | 204(삭제 성공), 400(필수값 누락), 401(비밀번호 불일치), 404(참조불가) |
| 소성    | 완료     | GET      | 본인 프로필 조회    | /api/member/profil                      | N/A                                                                                                       | { ”memberId” : 1, ”email” : “abc@naver.com”, ”nickname” : “5조5억”, ”country” : “South Korea” }           | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200 (OK), 404 (TOKEN_NOT_FOUND)                                        |
| 소성    | 완료     | GET      | 타인 프로필 조회    | /api/member/profil/{id}                 | {id} : targetId                                                                                           | { ”memberId”:2, ”email” : “def@naver.com”, ”nickname” : “4조4억”, ”country” : “United States” }           | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200 (OK), 404 (TOKEN_NOT_FOUND)                                        |
| 소성    | 완료     | PUT      | 프로필 수정         | /api/member/profil                      | { ”nickname” : “3조3억”, ”password” : “5Wh5djr!, ”country” : “United States” }                             | { “memberId”: 1, “message”: “프로필이 수정되었습니다.” }                                                   | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(OK), 401(비밀번호 불일치)                                          |
| 박상원  | 완료     | POST     | 게시물 작성         | /api/news                               | { ”title” : “첫 번째 게시글”, ”content” : “내용입니다!!” }                                                  | { "id": 1, "message": "작성되었습니다" }                                                                   | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(성공)                                                              |
| 박상원  | 완료     | GET      | 게시물 전체 조회    | /api/news?pageNo=1&pageSize=10           | N/A                                                                                                       | [ { ”id” : 1, ”title” : “제목입니다”, ”content” : “내용입니다”, ”authorNickname” : “5조5억”, ”commentCount” : “20”, ”modifyAt” : “YY-MM-DD” } ] | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(조회 성공), 404(존재하지 않음)                                     |
| 박상원  | 완료     | GET      | 게시물 전체 조회(기간조회) | /api/news?pageNo=1&pageSize=10&startDate=YYYY-MM-DD&endDate=YYYY-MM-DD | N/A                                                                                                       | [ { ”id” : 1, ”title” : “제목입니다”, ”content” : “내용입니다”, ”authorNickname” : “5조5억”, ”commentCount” : “20”, ”modifyAt” : “YY-MM-DD” } ] | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(조회 성공), 404(존재하지 않음)                                     |
| 박상원  | 완료     | GET      | 게시물 단건 조회    | /api/news/{newsid}                      | {id} : newsId                                                                                             | { ”title” : “제목입니다”, ”content” : “내용입니다”, ”nickname” : “5조5억”, ”modifyAt” : “YY-MM-DD”, ”commentList” : [ { ”commentId” : 1, ”memberId” : 1, ”newsId” : 1, ”comment” : “댓글 입니다.”, ”createAt” : “YY-MM-DD”, ”modifyAt” : “YY-MM-DD” } ] } | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(조회 성공), 404(존재하지 않음)                                     |
| 박상원  | 완료     | PUT      | 게시물 수정         | /api/news/{newsid}                      | { ”title” : “첫 번째 게시글 수정”, ”content” : “수정 내용입니다!!” }                                       | { "id": 1, "message": "수정되었습니다." }                                                                 | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(수정 성공), 401(권한 없음), 404(존재하지 않음)                      |
| 박상원  | 완료     | DELETE   | 게시물 삭제         | /api/news/{newsid}                      | {id} : newsId                                                                                             | { "id": 1, "message": "삭제되었습니다." }                                                                  | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 204(내용 없음), 401(권한 없음), 404(존재하지 않음)                      |
| 김명훈  | 완료     | POST     | 친구 신청           | /api/member/friends/request/{targetId}   | {id} : targetId                                                                                           | { ”memberId” : 1, ”targetId” : 2, ”message” : “친구 신청이 완료되었습니다” }                               | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(성공), 400(토큰 누락), 401(미유효토큰), 404(탈퇴한 유저)            |
| 김명훈  | 완료     | POST     | 친구 수락           | /api/member/friends/accept/{targetId}    | {id} : targetId                                                                                           | { ”memberId” : 1, ”targetId” : 2, ”message” : “친구 요청을 수락했습니다.” }                              | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(성공), 400(토큰 누락), 401(미유효토큰), 404(탈퇴한 유저)            |
| 김명훈  | 완료     | GET      | 친구 조회           | /api/member/friends                     | N/A                                                                                                       | { ”id” : 1, ”email” : “abc@naver.com”, ”nickname” : “4조4억”, ”country” : “United States” }              | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(성공), 400(토큰 누락), 401(미유효토큰), 404(탈퇴한 유저)            |
| 김명훈  | 완료     | DELETE   | 친구 삭제           | /api/member/friends/{targetId}           | {id} : targetId                                                                                           | { ”memberId” : 1, ”targetId” : 2, ”message” : “친구가 삭제되었습니다.” }                                  | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(성공), 400(토큰 누락), 401(미유효토큰), 404(탈퇴한 유저)            |
| 김명훈  | 완료     | GET      | 친구 게시물 전체조회 | /api/member/friends/news                | N/A                                                           | [ { "newsId": 3, "title": "첫번째 피드", "content": "내가 첫번째", "authorNickname": "테스트유저01", "commentsCount": 0, "modifiedAt": "2024-10-23T16:39:39.880039" } ] | **Authorization: Bearer {token}, Content-Type: application/json**      | **Content-Type: application/json**                    | 200(성공), 400(토큰 누락), 401(미유효토큰), 404(탈퇴한 유저)            |
| 조성준  | 완료     | POST     | 게시물 좋아요 누르기 | /api/news/like/{newsid}                 | {id} : newsId                                                                                             | { ”newsId” : 1, ”message” : “좋아요를 눌렀습니다.” }                                                     | **Authorization: Bearer {token}, Content-Type: application/json**      | **Content-Type: application/json**                                    | 200(성공), 401(자신의 게시글), 404(참조불가 뉴스), 409(이미 좋아요 상태) |
| 조성준  | 완료     | POST     | 댓글 좋아요 누르기   | /api/news/like/{newsid}/{commentid}      | {newsid} : newsId, {commentid} : commentId                                                                | { ”commentId” : 1, ”message” : “좋아요를 눌렀습니다.” }                                                  | **Authorization: Bearer {token}, Content-Type: application/json**      | **Content-Type: application/json**                                    | 200(성공), 401(자신의 댓글), 404(참조불가 댓글), 409(이미 좋아요 상태)   |
| 조성준  | 완료     | DELETE   | 게시물 좋아요 취소   | /api/news/like/{newsid}                 | {id} : newsId                                                                                             | { ”newsId” : 1, ”message” : “좋아요를 취소했습니다.” }                                                    | **Authorization: Bearer {token}, Content-Type: application/json**      | **Content-Type: application/json**                                    | 200(성공), 404(참조불가 뉴스)                                           |
| 조성준  | 완료     | DELETE   | 댓글 좋아요 취소     | /api/news/like/{newsid}/{commentid}      | {newsid} : newsId, {commentid} : commentId                                                                | { ”commentId” : 1, ”message” : “좋아요를 취소했습니다.” }                                                | **Authorization: Bearer {token}, Content-Type: application/json**      | **Content-Type: application/json**                                    | 200(성공), 404(참조불가 댓글)                                           |
| 박인선  | 완료     | POST     | 댓글 작성           | /api/news/comment/{commentId}            | { ”comment” : “댓글 입니다.” }                                                                             | { ”commentId” : 1, ”message” : “댓글을 생성했습니다.” }                                                  | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 201(작성성공)                                                           |
| 박인선  | 완료     | UPDATE   | 댓글 수정           | /api/news/comment/{commentId}            | { ”comment” : “댓글 수정 입니다.” }                                                                        | { ”commentId” : 1, ”message” : “댓글을 수정했습니다.” }                                                  | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(댓글 수정), 401(권한 없음)                                          |
| 박인선  | 완료     | DELETE   | 댓글 삭제           | /api/news/comment/{newsId}/{commentId}   | {newsid} : newsId, {commentid} : commentId                                                                | { ”commentId” : 1, ”message” : “댓글을 삭제했습니다.” }                                                  | **Authorization: Bearer {token}, Content-Type: application/json**      |                                                              | 200(댓글 삭제), 401(권한 없음)                                           |

---

### **기타 사항**

- **데이터베이스 마이그레이션**: `src/main/resources/application.properties` 파일에서 데이터베이스 설정을 맞춘 후 MySQL 데이터베이스를 준비해주세요.
- **테스트**: 모든 API는 `Postman`을 통해 테스트할 수 있습니다.
