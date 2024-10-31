package com.sparta.newsfeedproject.domain.like.service;

import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.comment.repository.CommentRepository;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.like.dto.LikeCommentResponseDto;
import com.sparta.newsfeedproject.domain.like.dto.LikeNewsResponseDto;
import com.sparta.newsfeedproject.domain.like.entity.CommentLike;
import com.sparta.newsfeedproject.domain.like.entity.NewsLike;
import com.sparta.newsfeedproject.domain.like.repository.CommentLikeRepository;
import com.sparta.newsfeedproject.domain.like.repository.NewsLikeRepository;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final NewsLikeRepository newsLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public LikeNewsResponseDto addNewsLike(Long newsId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        News news = newsRepository.findById(newsId).orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));
        if (news.isValidateCreator(member.getId())) {
            throw new CustomException(ErrorCode.CANNOT_LIKE_YOURSELF);
        }
        if (newsLikeRepository.existsByMemberIdAndNewsId(member.getId(), news.getId())) {
            throw new CustomException(ErrorCode.ALREADY_LIKE);
        }
        NewsLike newsLike = new NewsLike();
        newsLike.addLikeNews(member, news);
        newsLikeRepository.save(newsLike);
        return LikeNewsResponseDto
                .builder()
                .newsId(news.getId())
                .message("좋아요를 눌렀습니다.")
                .build();
    }

    @Transactional
    public LikeCommentResponseDto addCommentLike(Long commentId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if (comment.isValidateCreator(member.getId())) {
            throw new CustomException(ErrorCode.CANNOT_LIKE_YOURSELF);
        }
        if (commentLikeRepository.existsByMemberIdAndCommentId(member.getId(), comment.getId())) {
            throw new CustomException(ErrorCode.ALREADY_LIKE);
        }
        CommentLike commentLike = new CommentLike();
        commentLike.addLikeComment(member, comment);
        commentLikeRepository.save(commentLike);
        return LikeCommentResponseDto
                .builder()
                .commentId(comment.getId())
                .message("좋아요를 눌렀습니다.")
                .build();
    }

    public LikeNewsResponseDto removeNewsLike(Long newsId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        News news = newsRepository.findById(newsId).orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));
        NewsLike newsLike = newsLikeRepository.findByMemberIdAndNewsId(member.getId(), news.getId()).orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));
        newsLikeRepository.delete(newsLike);
        return LikeNewsResponseDto
                .builder()
                .newsId(news.getId())
                .message("좋아요를 취소했습니다.")
                .build();
    }

    public LikeCommentResponseDto removeCommentLike(Long commentId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        CommentLike commentLike = commentLikeRepository.findByMemberIdAndCommentId(member.getId(), comment.getId()).orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));
        commentLikeRepository.delete(commentLike);
        return LikeCommentResponseDto
                .builder()
                .commentId(comment.getId())
                .message("좋아요를 취소했습니다.")
                .build();
    }
}
