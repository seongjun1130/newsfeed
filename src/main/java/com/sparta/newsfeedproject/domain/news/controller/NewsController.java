package com.sparta.newsfeedproject.domain.news.controller;

import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import com.sparta.newsfeedproject.domain.news.dto.*;
import com.sparta.newsfeedproject.domain.news.service.NewsService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 뉴스 생성
    @PostMapping
    public ResponseEntity<NewsCreateResponseDTO> createNews(
            @Valid @RequestBody NewsCreateRequestDTO newsDTO,
            @LoginUser Member member) {
        NewsCreateResponseDTO createdNews = newsService.createNews(member, newsDTO);
        return ResponseEntity.ok(createdNews);
    }

    // 뉴스 단건 조회 (코멘트 포함)
    @GetMapping("/{newsid}")
    public ResponseEntity<NewsReadResponseDTO> getNews(@PathVariable("newsid") Long id) {
        NewsReadResponseDTO newsDTO = newsService.getNews(id);
        return ResponseEntity.ok(newsDTO);
    }

    @GetMapping
    public ResponseEntity<Page<NewsPageReadResponseDto>> getAllNews(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Page<NewsPageReadResponseDto> newsPage = newsService.getAllNews(pageNo, pageSize, startDate, endDate);
        return ResponseEntity.ok(newsPage);
    }

    // 뉴스 수정
    @PutMapping("/{newsid}")
    public ResponseEntity<NewsUpdateResponseDTO> updateNews(
            @PathVariable("newsid") Long id,
            @LoginUser Member member,
            @RequestBody NewsUpdateRequestDTO newsDTO) {
        NewsUpdateResponseDTO response = newsService.updateNews(id, member, newsDTO);
        return ResponseEntity.ok(response);
    }

    // 뉴스 삭제
    @DeleteMapping("/{newsid}")
    public ResponseEntity<NewsDeleteResponseDTO> deleteNews(@PathVariable("newsid") Long id, @LoginUser Member member) {
        NewsDeleteResponseDTO response = newsService.deleteNews(id, member);
        return ResponseEntity.ok(response);
    }
}
