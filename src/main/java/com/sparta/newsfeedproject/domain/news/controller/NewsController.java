package com.sparta.newsfeedproject.domain.news.controller;

import com.sparta.newsfeedproject.domain.news.dto.NewsDTO;
import com.sparta.newsfeedproject.domain.news.service.NewsService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 뉴스 생성
    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO, @RequestAttribute Member member) {
        NewsDTO createdNews = newsService.createNews(member, newsDTO);
        return ResponseEntity.ok(createdNews);
    }
}
