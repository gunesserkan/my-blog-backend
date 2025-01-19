package com.gunes.blog.controller;


import com.gunes.blog.model.dto.CreatePostRequest;
import com.gunes.blog.model.dto.PostResponse;
import com.gunes.blog.model.dto.UpdatePostRequest;
import com.gunes.blog.model.mapper.Mapper;
import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;
import com.gunes.blog.service.PostService;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Operation(summary = "returns all posts")
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {
        Page<PostResponse> postResponses = postService.getAll(pageable).map(Mapper::convertToPostResponseFrom);
        return ResponseEntity.ok(postResponses);
    }

    @Operation(summary = "returns a certain post by its id")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Mapper.convertToPostResponseFrom(postService.getById(id)));
    }

    @Operation(summary = "creates a post and adds its to the database then returns post's path")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest createPostRequest, Authentication auth) {
        PostResponse response = Mapper.convertToPostResponseFrom(
                postService.create(createPostRequest, auth.getName())
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Returns the updated post")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest, Authentication auth) {
        PostResponse response = Mapper.convertToPostResponseFrom(postService.updatePost(id, updatePostRequest, auth));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Doesn't return anything if the delete operation is successful")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication auth) {
        postService.deletePost(id, auth);
        return ResponseEntity.noContent().build();
    }
}
