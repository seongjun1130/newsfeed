package com.sparta.newsfeedproject.domain.like.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.news.entity.News;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public void addLikeNews(Member member, News news) {
        this.member = member;
        this.news = news;
    }

    public void addLikeComment(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }
}
