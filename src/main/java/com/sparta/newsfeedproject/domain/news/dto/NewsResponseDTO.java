package com.sparta.newsfeedproject.domain.news.dto;

import com.sparta.newsfeedproject.domain.comment.dto.CommentDTO;
import lombok.*;

import java.util.List;

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
    private List<CommentDTO> commentList;  // 코멘트 리스트 추가
}
