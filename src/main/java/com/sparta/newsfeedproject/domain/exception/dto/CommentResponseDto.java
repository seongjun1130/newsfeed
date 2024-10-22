package com.sparta.newsfeedproject.domain.exception.dto;

import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.news.entity.News;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private String comment;
    private Member member;
    private News news;
}
