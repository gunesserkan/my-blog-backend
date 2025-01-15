package com.gunes.blog.service;


import com.gunes.blog.model.dto.CommentResponse;
import com.gunes.blog.model.dto.CreateCommentRequest;
import com.gunes.blog.model.dto.UpdateCommentRequest;
import com.gunes.blog.model.entity.Comment;
import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;
import com.gunes.blog.model.mapper.Mapper;
import com.gunes.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    public CommentResponse getById(Long id) {
        Comment comment = commentRepository.getById(id);
        return Mapper.convertToCommentResponseFrom(comment);
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> Mapper.convertToCommentResponseFrom(comment))
                .collect(Collectors.toList());
    }

    public CommentResponse createComment(CreateCommentRequest req, String userName) {
        User user = userService.getByUsername(userName);
        Post post = postService.getById(req.postId());
        Comment comment = Comment.builder()
                .content(req.content())
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .post(post).build();
        return Mapper.convertToCommentResponseFrom(commentRepository.save(comment));
    }

    public CommentResponse updateComment(UpdateCommentRequest updatedComment,Long commentId) {
        Comment comment =commentRepository.getById(commentId);
        comment.setContent(updatedComment.content());
        comment.setUpdatedAt(LocalDateTime.now());
        return Mapper.convertToCommentResponseFrom(commentRepository.save(comment));
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
