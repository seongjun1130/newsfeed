package com.sparta.newsfeedproject.domain.exception.dto;

import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.news.entity.News;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String comment;
    private Member member;
    private News news;
}
