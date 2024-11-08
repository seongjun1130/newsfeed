package com.sparta.newsfeedproject.domain.member.repository;

import com.sparta.newsfeedproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailOrNickName(String email, String nickName);
    boolean existsByNickName(String nickName);
    Optional<Member> findByEmail(String email);
}
