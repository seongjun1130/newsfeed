package com.sparta.newsfeedproject.domain.news.controller;

import com.sparta.newsfeedproject.domain.member.resolver.util.LoginUser;
import com.sparta.newsfeedproject.domain.news.dto.NewsCreateRequestDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsCreateResponseDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsPageReadResponseDto;
import com.sparta.newsfeedproject.domain.news.dto.NewsReadResponseDTO;
import com.sparta.newsfeedproject.domain.news.service.NewsService;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    @GetMapping("/{id}")
    public ResponseEntity<NewsReadResponseDTO> getNews(@PathVariable Long id) {
        NewsReadResponseDTO newsDTO = newsService.getNews(id);
        return ResponseEntity.ok(newsDTO);
    }

    // 뉴스 전체 조회 (코멘트 포함)
    @GetMapping
    public ResponseEntity<Page<NewsPageReadResponseDto>> getAllNews(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // 정렬 방향 설정 (DESC, ASC)
        Sort.Direction direction = Sort.Direction.fromString(sortDir);

        // 뉴스 조회 서비스 호출
        Page<NewsPageReadResponseDto> newsPage = newsService.getAllNews(pageNo, pageSize, direction, startDate, endDate);
        return ResponseEntity.ok(newsPage);
    }



    // 뉴스 수정
    @PutMapping("/{id}")
    public ResponseEntity<NewsReadResponseDTO> updateNews(
            @PathVariable Long id,
            @Valid @RequestBody NewsCreateRequestDTO newsDTO,
            @LoginUser Member member) {
        NewsReadResponseDTO updatedNews = newsService.updateNews(id, member, newsDTO);
        return ResponseEntity.ok(updatedNews);
    }


    // 뉴스 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id, @LoginUser Member member) {
        newsService.deleteNews(id, member);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
