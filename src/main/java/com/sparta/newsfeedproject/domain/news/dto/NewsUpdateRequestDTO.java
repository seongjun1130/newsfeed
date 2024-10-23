package com.sparta.newsfeedproject.domain.news.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsUpdateRequestDTO {
    private String title;
    private String content;
}
