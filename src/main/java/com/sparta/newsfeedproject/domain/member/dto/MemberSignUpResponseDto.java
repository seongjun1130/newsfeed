package com.sparta.newsfeedproject.domain.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpResponseDto {
    private String nickName;
    private Long id;
}
