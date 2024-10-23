package com.sparta.newsfeedproject.domain.news.service;

import com.sparta.newsfeedproject.domain.comment.dto.CommentDTO;
import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import com.sparta.newsfeedproject.domain.news.dto.*;
import com.sparta.newsfeedproject.domain.news.entity.News;
import com.sparta.newsfeedproject.domain.news.repository.NewsRepository;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional
    public NewsCreateResponseDTO createNews(Member member, NewsCreateRequestDTO newsDTO) {
        News news = News.builder()
                .member(member)
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .build();
        newsRepository.save(news);
        return mapToCrateResponseDTO(news);
    }


    @Transactional(readOnly = true)
    public Page<NewsPageReadResponseDto> getAllNews(int pageNo, int pageSize, LocalDate startDate, LocalDate endDate) {
        PageRequest pageable;

        if (startDate != null && endDate != null) {
            // 날짜가 주어졌을 때 modifiedAt 기준으로 정렬
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "modifiedAt"));
            return newsRepository.findAllByModifiedAtBetween(
                    startDate.atStartOfDay(),
                    endDate.atTime(23, 59, 59),
                    pageable).map(this::mapToReadPageResponseDTO);
        } else {
            // 날짜가 없을 경우 id 기준으로 내림차순 정렬
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            return newsRepository.findAll(pageable).map(this::mapToReadPageResponseDTO);
        }
    }


    @Transactional(readOnly = true)
    public NewsReadResponseDTO getNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));  // CustomException 사용

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

    @Transactional
    public NewsUpdateResponseDTO updateNews(Long id, Member member, NewsUpdateRequestDTO newsDTO) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));

        if (!news.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        newsRepository.save(news);

        return NewsUpdateResponseDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorNickname(news.getMember().getNickName())
                .modifyAt(news.getModifiedAt())
                .build();
    }

    @Transactional
    public void deleteNews(Long id, Member member) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NEWS_NOT_FOUND));

        // 작성자가 일치하는지 검증
        if (!news.isAuthor(member)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 작성자가 맞다면 게시물 삭제
        newsRepository.delete(news);
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