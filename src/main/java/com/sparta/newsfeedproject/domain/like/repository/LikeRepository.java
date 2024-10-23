package com.sparta.newsfeedproject.domain.like.repository;

import com.sparta.newsfeedproject.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByMemberIdAndNewsId(Long memberId, Long newsId);

    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);

    Optional<Like> findByMemberIdAndNewsId(Long memberId, Long newsId);

    Optional<Like> findByMemberIdAndCommentId(Long memberId, Long commentId);

    void deleteByMemberId(Long id);
}
