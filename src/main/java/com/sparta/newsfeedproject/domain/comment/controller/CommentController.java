package com.sparta.newsfeedproject.domain.comment.controller;

import com.sparta.newsfeedproject.domain.comment.service.CommentService;
import com.sparta.newsfeedproject.domain.exception.dto.CommentRequestDto;
import com.sparta.newsfeedproject.domain.exception.dto.CommentResponseDto;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news/")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("{id}")
    public ResponseEntity<CommentResponseDto> creatComment(@Valid @RequestBody CommentRequestDto requestDto, @LoginUser Member member, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(requestDto, member.getId(),id));
    }

    // 댓글 수정하기
    @PutMapping("")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentRequestDto requestDto, @PathVariable Long id,@PathVariable Long newsId) {
        commentService.updateComment(requestDto, id, newsId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 댓글 삭제하기
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long newsId, @PathVariable Long commentId) {
        commentService.deleteComment(newsId, commentId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
}

}
