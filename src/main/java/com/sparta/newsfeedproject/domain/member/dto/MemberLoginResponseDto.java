package com.sparta.newsfeedproject.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLoginResponseDto {
    private String token;
    private String nickName;
    private Long id;
}
