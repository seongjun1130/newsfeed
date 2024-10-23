package com.sparta.newsfeedproject.domain.like.repository;

import com.sparta.newsfeedproject.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByMemberIdAndNewsId(Long memberId, Long newsId);

    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
}
