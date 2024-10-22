package com.sparta.newsfeedproject.domain.member.dto;

import com.sparta.newsfeedproject.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberProfileResponseDto {
    private String email;
    private String nickname;
    private String country;

    public MemberProfileResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickName();
        this.country = member.getCountry();
    }
}
