package com.sparta.newsfeedproject.domain.like.service;

import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.comment.repository.CommentRepository;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.like.dto.LikeCommentResponseDto;
import com.sparta.newsfeedproject.domain.like.dto.LikeNewsResponseDto;
import com.sparta.newsfeedproject.domain.like.entity.Like;
import com.sparta.newsfeedproject.domain.like.repository.LikeRepository;
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
    private final LikeRepository likeRepository;
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
        if (likeRepository.existsByMemberIdAndNewsId(member.getId(), news.getId())) {
            throw new CustomException(ErrorCode.ALREADY_LIKE);
        }
        Like like = new Like();
        like.addLikeNews(member, news);
        likeRepository.save(like);
        return LikeNewsResponseDto
                .builder()
                .newsId(news.getId())
                .message("좋아요를 눌렀습니다.")
                .build();
    }

    @Transactional
    public LikeCommentResponseDto addCommentLike(Long newsId, Long commentId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        News news = newsRepository.findById(newsId).orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if (comment.isValidateCreator(member.getId())) {
            throw new CustomException(ErrorCode.CANNOT_LIKE_YOURSELF);
        }
        if (likeRepository.existsByMemberIdAndCommentId(member.getId(), comment.getId())) {
            throw new CustomException(ErrorCode.ALREADY_LIKE);
        }
        Like like = new Like();
        like.addLikeComment(member, comment);
        likeRepository.save(like);
        return LikeCommentResponseDto
                .builder()
                .commentId(commentId)
                .message("좋아요를 눌렀습니다.")
                .build();
    }
}
