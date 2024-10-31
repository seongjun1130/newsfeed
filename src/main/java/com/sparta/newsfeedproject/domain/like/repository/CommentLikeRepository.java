package com.sparta.newsfeedproject.domain.like.repository;

import com.sparta.newsfeedproject.domain.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
    Optional<CommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId);
    void deleteByMemberId(Long id);
}
