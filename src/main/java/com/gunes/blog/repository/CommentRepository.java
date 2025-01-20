package com.gunes.blog.repository;

import com.gunes.blog.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByPostId(Long postId);
    Optional<Comment> findCommentById(Long id);
}
