package com.sparta.newsfeedproject.domain.news.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Setter
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
    @Column(name ="title", nullable = false,length = 50)
    private String title;
    @Column(name ="content", nullable = false,length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
