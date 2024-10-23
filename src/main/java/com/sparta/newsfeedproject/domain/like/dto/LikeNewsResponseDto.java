package com.sparta.newsfeedproject.domain.like.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeNewsResponseDto {
    private Long newsId;
    private String message;
}
