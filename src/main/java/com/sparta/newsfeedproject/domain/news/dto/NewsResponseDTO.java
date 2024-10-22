package com.sparta.newsfeedproject.domain.news.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private String modifyAt;
}
