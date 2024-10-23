package com.sparta.newsfeedproject.domain.news.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class NewsUpdateResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private LocalDateTime modifyAt;
}
