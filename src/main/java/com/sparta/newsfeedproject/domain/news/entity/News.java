package com.sparta.newsfeedproject.domain.news.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "news")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class News extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, length = 100)
    private String title;

    @Column( nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments;

}
