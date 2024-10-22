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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 뉴스 전체 조회 (페이지네이션 + 정렬)
    @GetMapping
    public ResponseEntity<Page<NewsPageReadResponseDto>> getAllNews(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "DESC") String sortDirection // 추가된 정렬 방식 파라미터
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<NewsPageReadResponseDto> newsPage = newsService.getAllNews(pageNo, pageSize, direction);
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
    public ResponseEntity<NewsReadResponseDTO> getNews(@PathVariable Long id) {
        NewsReadResponseDTO newsDTO = newsService.getNews(id);
        return ResponseEntity.ok(newsDTO);
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
        return ResponseEntity.noContent().build();
    }
}
