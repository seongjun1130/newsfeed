package com.sparta.newsfeedproject.domain.like.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeCommentResponseDto {
    private Long commentId;
    private String message;
}
