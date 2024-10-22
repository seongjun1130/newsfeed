package com.sparta.newsfeedproject.domain.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String comment;
    private String authorNickname;
    private String createdAt;
}
