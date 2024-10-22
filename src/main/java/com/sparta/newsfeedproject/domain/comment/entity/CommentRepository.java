package com.sparta.newsfeedproject.domain.comment.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findCommentById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("member not found:" + id));
    }

}
