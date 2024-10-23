package com.sparta.newsfeedproject.domain.news.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsDeleteResponseDTO {
    private Long id;
    private String message;
}
