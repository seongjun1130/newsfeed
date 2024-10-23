package com.sparta.newsfeedproject.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDeleteResponseDto {
    private Long id;
    private String message;
}
