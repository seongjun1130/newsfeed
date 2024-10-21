package com.sparta.newsfeedproject.domain.news.service;

import com.sparta.newsfeedproject.domain.news.dto.NewsDTO;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional
    public NewsDTO createNews(Member author, NewsDTO newsDTO) {
        News news = News.builder()
                .author(author)
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .build();
        newsRepository.save(news);

        return mapToDTO(news);
    }

    private NewsDTO mapToDTO(News news) {
        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorNickname(news.getAuthor().getNickName())
                .modifyAt(news.getModifiedAt().toString())
                .build();
    }
}
