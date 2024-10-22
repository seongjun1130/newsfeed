package com.sparta.newsfeedproject.domain.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String comment;
    private String authorNickname;
    private LocalDateTime modifiedAt;  // LocalDateTime으로 변경
}
