package com.sparta.newsfeedproject.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String message;
}
