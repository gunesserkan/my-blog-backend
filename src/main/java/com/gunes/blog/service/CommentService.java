package com.gunes.blog.service;

import com.gunes.blog.model.dto.CreateCommentRequest;
import com.gunes.blog.model.dto.UpdateCommentRequest;
import com.gunes.blog.model.entity.Comment;
import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;
import com.gunes.blog.model.enums.Role;
import com.gunes.blog.repository.CommentRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Comment getById(Long id) {
        return commentRepository.findCommentById(id).orElseThrow(EntityExistsException::new);
    }

    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findCommentByPostId(postId);
    }

    public Comment createComment(CreateCommentRequest req,Long postId, String userName) {
        User user = userService.getByUsername(userName);
        Post post = postService.getById(postId);
        Comment comment = Comment.builder()
                .content(req.content())
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .post(post).build();
        return commentRepository.save(comment);
    }

    public Comment updateComment(UpdateCommentRequest updatedComment, Long commentId, Authentication auth) {
        Comment comment = commentRepository.findCommentById(commentId).orElseThrow(EntityExistsException::new);
        if (!comment.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You do not have permission to update this comment");
        }
        comment.setContent(updatedComment.content());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteById(Long id, Authentication auth) {
        Comment comment = commentRepository.findCommentById(id).orElseThrow(EntityExistsException::new);
        if (!comment.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }
        commentRepository.deleteById(id);
    }
}
