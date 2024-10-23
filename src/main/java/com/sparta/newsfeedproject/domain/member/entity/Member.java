package com.sparta.newsfeedproject.domain.member.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.config.security.PasswordEncoder;
import com.sparta.newsfeedproject.domain.member.eunm.MembershipStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MembershipStatus status;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    public boolean isValidPassword(String password, PasswordEncoder pwEncoder) {
        return pwEncoder.matches(password, this.password);
    }

    public boolean isInactive() {
        return status == MembershipStatus.INACTIVE;
    }

    public void anonymizeMember() {
        this.setNickName("anonymous_" + this.getNickName() + this.getId());
        this.setDeletedAt(LocalDateTime.now());
        this.setStatus(MembershipStatus.INACTIVE);
    }

    public void update(String nickname, String country) {
        this.nickName = nickname;
        this.country = country;
    }
}
