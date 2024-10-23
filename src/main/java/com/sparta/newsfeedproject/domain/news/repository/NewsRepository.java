package com.sparta.newsfeedproject.domain.news.repository;

import com.sparta.newsfeedproject.domain.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    void deleteByMemberId(Long id);

    Page<News> findAllByModifiedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

}
