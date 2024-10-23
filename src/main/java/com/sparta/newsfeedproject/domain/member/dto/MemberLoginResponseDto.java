package com.sparta.newsfeedproject.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLoginResponseDto {
    private Long id;
    private String message;
    private String token;
}
