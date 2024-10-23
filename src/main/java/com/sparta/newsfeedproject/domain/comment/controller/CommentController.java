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
@RequestMapping("/api/news/comment/")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("{commentid}")
    public ResponseEntity<CommentResponseDto> creatComment(@Valid @RequestBody CommentRequestDto requestDto, @LoginUser Member member, @PathVariable("commentid") Long newsId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(requestDto, member.getId(),newsId));
    }

     //댓글 수정하기
    @PutMapping("{commentid}")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentRequestDto requestDto, @LoginUser Member member,@PathVariable("commentid") Long newsId) {
        return ResponseEntity
                .status(HttpStatus.OK).body(commentService.updateComment(requestDto, member.getId(), newsId));

    }

     //댓글 삭제하기

    @DeleteMapping("{newsId}/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable("newsId") Long newsId, @PathVariable("commentId") Long commentId) {
        return ResponseEntity
                .status(HttpStatus.OK).body(commentService.deleteComment(newsId, commentId));
}

}
