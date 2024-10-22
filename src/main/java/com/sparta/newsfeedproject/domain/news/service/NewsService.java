package com.sparta.newsfeedproject.domain.news.service;

import com.sparta.newsfeedproject.domain.comment.dto.CommentDTO;
import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
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

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));  // CustomException 사용

        // Comment 리스트를 DTO로 변환
        List<CommentDTO> commentList = news.getComments().stream()
                .map(this::mapToCommentDTO)
                .collect(Collectors.toList());

        return NewsResponseDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorNickname(news.getAuthor().getNickName())
                .modifyAt(news.getModifiedAt().toString())
                .commentList(commentList)  // 코멘트 리스트 추가
                .build();
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

    private CommentDTO mapToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .authorNickname(comment.getMember().getNickName())
                .createdAt(comment.getModifiedAt().toString())  // 변경된 날짜 사용
                .build();
    }
}
