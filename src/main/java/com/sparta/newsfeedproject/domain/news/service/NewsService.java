package com.sparta.newsfeedproject.domain.news.service;

import com.sparta.newsfeedproject.domain.news.dto.NewsRequestDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsResponseDTO;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional
    public NewsResponseDTO createNews(Member author, NewsRequestDTO newsDTO) {
        News news = News.builder()
                .author(author)
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .build();
        newsRepository.save(news);
        return mapToResponseDTO(news);
    }

    @Transactional(readOnly = true)
    public Page<NewsResponseDTO> getAllNews(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        return newsRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    @Transactional(readOnly = true)
    public NewsResponseDTO getNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found"));
        return mapToResponseDTO(news);
    }

    private NewsResponseDTO mapToResponseDTO(News news) {
        return NewsResponseDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorNickname(news.getAuthor().getNickName())
                .modifyAt(news.getModifiedAt().toString())
                .build();
    }
}
