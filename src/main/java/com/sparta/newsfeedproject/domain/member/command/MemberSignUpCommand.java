package com.sparta.newsfeedproject.domain.member.command;

import com.sparta.newsfeedproject.domain.member.dto.MemberSignUpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MemberSignUpCommand {
    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;
    private String country;

    public MemberSignUpCommand(MemberSignUpRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.nickName = requestDto.getNickName();
        this.password = requestDto.getPassword();
        this.country = requestDto.getCountry();
    }
}
