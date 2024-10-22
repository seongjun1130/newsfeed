package com.sparta.newsfeedproject.domain.member.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.config.security.PasswordEncoder;
import com.sparta.newsfeedproject.domain.member.eunm.MembershipStatus;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;
    @Column(name = "nickname", unique = true, nullable = false, length = 50)
    private String nickName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "country", nullable = false, length = 15)
    private String country;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MembershipStatus status;


    public boolean isValidPassword(String password, PasswordEncoder pwEncoder) {
        return pwEncoder.matches(password, this.password);
    }
}
