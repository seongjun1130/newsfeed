package com.sparta.newsfeedproject.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendNewsResponseDto {
    private Long newsId;
    private String title;
    private String content;
    private String authorNickname;
    private int commentsCount;
    private LocalDateTime modifiedAt;
}
