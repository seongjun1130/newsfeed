package com.sparta.newsfeedproject.domain.comment.entity;

import com.sparta.newsfeedproject.domain.exception.dto.CommentRequestDto;
import com.sparta.newsfeedproject.domain.exception.dto.CommentResponseDto;
import com.sparta.newsfeedproject.domain.member.entity.Member;
import com.sparta.newsfeedproject.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Comment comment = Comment.from(commentRequestDto, member);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.to();
    }

    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long memberId, Long commentId) {
        memberRepository.findById(memberId);
        Comment comment = commentRepository.findCommentById(commentId);
        comment.updatedata(commentRequestDto);
    }
    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        memberRepository.findById(memberId);
        commentRepository.findCommentById(commentId);
        commentRepository.deleteById(commentId);
    }

}
