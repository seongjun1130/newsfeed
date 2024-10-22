package com.sparta.newsfeedproject.domain.comment.service;

import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.comment.repository.CommentRepository;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.dto.CommentRequestDto;
import com.sparta.newsfeedproject.domain.exception.dto.CommentResponseDto;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long memberId, Long newsId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        News news = newsRepository.findById(newsId).orElseThrow(()->new CustomException(ErrorCode.NEWS_NOT_FOUND));
        Comment comment = Comment.from(commentRequestDto, member,news);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.to();
    }

//    @Transactional
//    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long memberId, Long commentId) {
//        memberRepository.findById(memberId);
//        Comment comment = commentRepository.findCommentById(commentId);
//        comment.updatedata(commentRequestDto);
//        return new CommentResponseDto(commentId);
//    }
//    @Transactional
//    public void deleteComment(Long memberId, Long commentId) {
//        memberRepository.findById(memberId);
//        commentRepository.findCommentById(commentId);
//        commentRepository.deleteById(commentId);
//    }

}
