package com.sparta.newsfeedproject.domain.comment.service;

import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.comment.repository.CommentRepository;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.comment.dto.CommentRequestDto;
import com.sparta.newsfeedproject.domain.comment.dto.CommentResponseDto;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long memberId, Long newsId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        News news = newsRepository.findById(newsId).orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));
        Comment comment = Comment.from(commentRequestDto, member, news);
        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .message("댓글이 생성되었습니다").build();
    }


    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long memberId, Long commentId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findCommentById(commentId);
        authorizedArticleAuthor(member, comment);
        comment.updatedata(commentRequestDto);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .message("댓글이 수정되었습니다").build();
    }

    @Transactional
    public CommentResponseDto deleteComment(Long memberId, Long newsId, Long commentId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findCommentById(commentId);
        authorizedArticleAuthor(member, comment);
        commentRepository.deleteById(commentId);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .message("댓글이 삭제되었습니다").build();
    }

    private void authorizedArticleAuthor(Member member, Comment comment) {
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);  // 권한이 없을 때 예외 발생
        }
    }

}
