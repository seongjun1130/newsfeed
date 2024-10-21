package com.sparta.newsfeedproject.domain.news.controller;

import com.sparta.newsfeedproject.domain.news.dto.NewsDTO;
import com.sparta.newsfeedproject.domain.news.service.NewsService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // 뉴스 전체 조회 (페이지네이션 포함)
    @GetMapping
    public ResponseEntity<Page<NewsDTO>> getAllNews(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<NewsDTO> newsPage = newsService.getAllNews(pageNo, pageSize);
        return ResponseEntity.ok(newsPage);
    }

    // 특정 뉴스 조회
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        NewsDTO newsDTO = newsService.getNews(id);
        return ResponseEntity.ok(newsDTO);
    }
}
