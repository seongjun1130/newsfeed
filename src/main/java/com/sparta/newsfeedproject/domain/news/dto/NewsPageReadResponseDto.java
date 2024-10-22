package com.sparta.newsfeedproject.domain.news.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsPageReadResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private int commentCount;
    private LocalDateTime modifyAt;
}
