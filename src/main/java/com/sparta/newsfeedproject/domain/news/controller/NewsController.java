package com.sparta.newsfeedproject.domain.news.controller;

import com.sparta.newsfeedproject.domain.news.dto.NewsRequestDTO;
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
    public ResponseEntity<NewsResponseDTO> createNews(
            @Valid @RequestBody NewsRequestDTO newsDTO,
            @RequestAttribute Member member) {
        NewsResponseDTO createdNews = newsService.createNews(member, newsDTO);
        return ResponseEntity.ok(createdNews);
    }

    // 뉴스 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNews(@PathVariable Long id) {
        NewsResponseDTO newsDTO = newsService.getNews(id);
        return ResponseEntity.ok(newsDTO);
    }

    // 뉴스 수정
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> updateNews(
            @PathVariable Long id,
            @Valid @RequestBody NewsRequestDTO newsDTO,
            @RequestAttribute Member member) {
        NewsResponseDTO updatedNews = newsService.updateNews(id, member, newsDTO);
        return ResponseEntity.ok(updatedNews);
    }

    // 뉴스 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id, @RequestAttribute Member member) {
        newsService.deleteNews(id, member);
        return ResponseEntity.noContent().build();
    }
}
