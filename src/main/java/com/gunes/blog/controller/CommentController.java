package com.gunes.blog.controller;


import com.gunes.blog.model.dto.CommentResponse;
import com.gunes.blog.model.dto.CreateCommentRequest;
import com.gunes.blog.model.dto.UpdateCommentRequest;
import com.gunes.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Operation(summary = "Returns all the comments which are related with the current post")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable int postId) {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Returns a comment by Id")
    public ResponseEntity<CommentResponse> getComment(@PathVariable int postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getById(commentId));
    }

    @PostMapping
    @Operation(summary = "Returns a location where the created comment is and the comment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CreateCommentRequest createRequest, Authentication auth){
        CommentResponse createdComment=commentService.createComment(createRequest,auth.getName());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{commentId}")
                .buildAndExpand(createdComment.id())
                .toUri();
        return ResponseEntity.created(location).body(createdComment);
    }
    @PutMapping("/{commentId}")
    @Operation(summary = "Returns the updated comment")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody UpdateCommentRequest updateRequest,@PathVariable Long commentId){
        CommentResponse updatedComment=commentService.updateComment(updateRequest,commentId);
        return ResponseEntity.ok(updatedComment);
    }
    @DeleteMapping("/{commentId}")
    @Operation(summary = "Returns no content response")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }
}
