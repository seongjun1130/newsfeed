package com.sparta.newsfeedproject.domain.like.controller;

import com.sparta.newsfeedproject.domain.like.dto.LikeCommentResponseDto;
import com.sparta.newsfeedproject.domain.like.dto.LikeNewsResponseDto;
import com.sparta.newsfeedproject.domain.like.service.LikeService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news/like/{newsid}")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeNewsResponseDto> addLikeNews(@PathVariable("newsid") final Long newsId, @LoginUser Member member) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(likeService.addNewsLike(newsId, member.getId()));
    }

    @PostMapping("/{commentid}")
    public ResponseEntity<LikeCommentResponseDto> addLikeComment(@PathVariable("newsid") final Long newsId, @PathVariable("commentid") final Long commentId, @LoginUser Member member) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(likeService.addCommentLike(newsId, commentId, member.getId()));
    }
}
