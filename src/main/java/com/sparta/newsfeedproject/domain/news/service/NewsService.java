package com.sparta.newsfeedproject.domain.news.service;

import com.sparta.newsfeedproject.domain.comment.dto.CommentDTO;
import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.news.dto.NewsCreateRequestDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsCreateResponseDTO;
import com.sparta.newsfeedproject.domain.news.dto.NewsPageReadResponseDto;
import com.sparta.newsfeedproject.domain.news.dto.NewsReadResponseDTO;
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
    public NewsCreateResponseDTO createNews(Member author, NewsCreateRequestDTO newsDTO) {
        News news = News.builder()
                .member(author)
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .build();
        newsRepository.save(news);
        return mapToCrateResponseDTO(news);
    }

    @Transactional(readOnly = true)
    public Page<NewsPageReadResponseDto> getAllNews(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        return newsRepository.findAll(pageable).map(this::mapToReadPageResponseDTO);
    }

    @Transactional(readOnly = true)
    public NewsReadResponseDTO getNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));  // CustomException 사용

        // Comment 리스트를 DTO로 변환
        List<CommentDTO> commentList = news.getComments().stream()
                .map(this::mapToCommentDTO)
                .toList();

        return NewsReadResponseDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorNickname(news.getMember().getNickName())
                .modifyAt(news.getModifiedAt())
                .commentList(commentList)  // 코멘트 리스트 추가
                .build();
    }

    private NewsCreateResponseDTO mapToCrateResponseDTO(News news) {
        return NewsCreateResponseDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .build();
    }

    private NewsPageReadResponseDto mapToReadPageResponseDTO(News news) {
        return NewsPageReadResponseDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorNickname(news.getMember().getNickName())
                .modifyAt(news.getModifiedAt())
                .commentCount(news.getComments().size())
                .build();
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .authorNickname(comment.getMember().getNickName())
                .modifiedAt(comment.getModifiedAt())  // LocalDateTime 로 변경함
                .build();
    }

}
