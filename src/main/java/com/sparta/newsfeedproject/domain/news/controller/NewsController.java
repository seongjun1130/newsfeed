package com.sparta.newsfeedproject.domain.news.controller;

import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import com.sparta.newsfeedproject.domain.news.dto.NewsCreateRequestDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsCreateResponseDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsResponseDTO;
import com.sparta.newsfeedproject.domain.news.service.NewsService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 뉴스 전체 조회 (페이지네이션 + 정렬)
    @GetMapping
    public ResponseEntity<Page<NewsResponseDTO>> getAllNews(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<NewsResponseDTO> newsPage = newsService.getAllNews(pageNo, pageSize);
        return ResponseEntity.ok(newsPage);
    }

    // 뉴스 생성
    @PostMapping
    public ResponseEntity<NewsCreateResponseDTO> createNews(
            @Valid @RequestBody NewsCreateRequestDTO newsDTO,
            @LoginUser Member member) {
        NewsCreateResponseDTO createdNews = newsService.createNews(member, newsDTO);
        return ResponseEntity.ok(createdNews);
    }

    // 뉴스 단건 조회 (코멘트 포함)
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNews(@PathVariable Long id) {
        NewsResponseDTO newsDTO = newsService.getNews(id);
        return ResponseEntity.ok(newsDTO);
    }
}
