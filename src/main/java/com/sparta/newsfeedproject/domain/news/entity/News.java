package com.sparta.newsfeedproject.domain.news.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@Table(name = "news")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class News extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public void updateNews(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
