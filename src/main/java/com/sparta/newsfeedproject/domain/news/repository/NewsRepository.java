package com.sparta.newsfeedproject.domain.news.repository;

import com.sparta.newsfeedproject.domain.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NewsRepository extends JpaRepository<News, Long> {
    // 날짜 범위로 뉴스 조회
    Page<News> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
