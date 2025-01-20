package com.gunes.blog.controller;


import com.gunes.blog.model.dto.CommentResponse;
import com.gunes.blog.model.dto.CreateCommentRequest;
import com.gunes.blog.model.dto.UpdateCommentRequest;
import com.gunes.blog.model.mapper.Mapper;
import com.gunes.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Operation(summary = "Returns all the comments which are related with the current post")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok(
                commentService.getAllComments(postId).stream()
                        .map(comment -> Mapper.toCommentResponse(comment))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Returns a comment by Id")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(Mapper.toCommentResponse(commentService.getById(commentId)));
    }

    @PostMapping
    @Operation(summary = "Returns a location where the created comment is and the comment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CreateCommentRequest createRequest, @PathVariable Long postId, Authentication auth) {
        CommentResponse createdComment = Mapper.toCommentResponse(
                commentService.createComment(createRequest, postId, auth.getName())
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{commentId}")
                .buildAndExpand(createdComment.id())
                .toUri();
        return ResponseEntity.created(location).body(createdComment);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Returns the updated comment")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody UpdateCommentRequest updateRequest, @PathVariable Long commentId, Authentication auth) {
        CommentResponse updatedComment = Mapper.toCommentResponse(commentService.updateComment(updateRequest, commentId, auth));
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Returns no content response")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, Authentication auth) {
        commentService.deleteById(commentId, auth);
        return ResponseEntity.noContent().build();
    }
}
