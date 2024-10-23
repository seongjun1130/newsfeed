package com.sparta.newsfeedproject.domain.comment.repository;

import com.sparta.newsfeedproject.domain.comment.entity.Comment;
import com.sparta.newsfeedproject.domain.exception.CustomException;
import com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findCommentById(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
