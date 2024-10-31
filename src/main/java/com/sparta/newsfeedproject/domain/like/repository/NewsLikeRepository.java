package com.sparta.newsfeedproject.domain.like.repository;

import com.sparta.newsfeedproject.domain.like.entity.NewsLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsLikeRepository extends JpaRepository<NewsLike, Long> {
    boolean existsByMemberIdAndNewsId(Long memberId, Long newsId);
    Optional<NewsLike> findByMemberIdAndNewsId(Long memberId, Long newsId);
    void deleteByMemberId(Long id);
}
