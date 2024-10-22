package com.sparta.newsfeedproject.domain.comment.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.exception.dto.CommentRequestDto;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.news.entity.News;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment", nullable = false, length = 100)
    private String comment;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "news_id")
    private News news;

    public static Comment from(CommentRequestDto commentRequestDto, Member member, News news) {
        Comment comment = new Comment();
        comment.initData(commentRequestDto, member, news);
        return comment;
    }
    private void initData(CommentRequestDto commentRequestDto, Member member, News news) {
        this.comment = commentRequestDto.getComment();
        this.member = commentRequestDto.getMember();
        this.news = news;
    }

    public CommentRequestDto to(){
        return new CommentRequestDto(){
            this.id,
            this.member,
            this.news
        );
    }

}
