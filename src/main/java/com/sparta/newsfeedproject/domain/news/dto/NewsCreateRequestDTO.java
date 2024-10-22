package com.sparta.newsfeedproject.domain.news.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsCreateRequestDTO {

    @NotBlank(message = "타이틀을 채워야합니다.")
    private String title;

    @NotBlank(message = "content가 채워져야합니다.")
    private String content;
}
