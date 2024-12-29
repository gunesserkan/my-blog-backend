package com.gunes.blog.controller;


import com.gunes.blog.dto.CreatePostRequest;
import com.gunes.blog.dto.PostResponse;
import com.gunes.blog.mapper.Mapper;
import com.gunes.blog.model.Post;
import com.gunes.blog.model.User;
import com.gunes.blog.service.PostService;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
        Page<Post> posts = postService.getAll(pageable);
        Page<PostResponse> postResponses = posts.map(Mapper::convertToPostResponseFrom);
        return ResponseEntity.ok(postResponses);
    }
    @Operation(summary = "returns a certain post by its id")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(Mapper.convertToPostResponseFrom(postService.getById(id)));
    }
    @Operation(summary = "creates a post and adds its to the database then returns post's path")
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest requestedPost, Principal principal) {
        User user = userService.getByUsername(principal.getName()).get();
        Post post = Post.builder()
                .title(requestedPost.title())
                .content(requestedPost.content())
                .createdAt(LocalDateTime.now())
                .user(user).build();
        Post savedPost = postService.create(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
