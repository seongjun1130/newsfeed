package com.sparta.newsfeedproject.domain.news.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsCreateResponseDTO {
    private Long id;
    private String message;
}
