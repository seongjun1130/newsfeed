package com.sparta.newsfeedproject.domain.comment.entity;

import com.sparta.newsfeedproject.domain.audit.Auditable;
import com.sparta.newsfeedproject.domain.comment.dto.CommentRequestDto;
import com.sparta.newsfeedproject.domain.like.entity.CommentLike;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.news.entity.News;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CommentLike> likes = new ArrayList<>();

    // 댓글 객체 생성
    public static Comment from(CommentRequestDto commentRequestDto, Member member, News news) {
        Comment comment = new Comment();
        comment.initData(commentRequestDto, member, news);
        return comment;
    }

    // 초기화 메서드
    private void initData(CommentRequestDto commentRequestDto, Member member, News news) {
        this.comment = commentRequestDto.getComment();
        this.member = member;
        this.news = news;

    }

    // 댓글 내용 수정
    public void updatedata(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public boolean isValidateCreator(Long memberId) {
        if (this.getMember().getId().equals(memberId)) {
            return true;
        }
        return false;
    }


}
