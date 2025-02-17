package com.gunes.blog.controller;


import com.gunes.blog.model.dto.request.CreatePostDto;
import com.gunes.blog.model.dto.response.PostDto;
import com.gunes.blog.model.dto.request.UpdatePostDto;
import com.gunes.blog.model.mapper.Mapper;
import com.gunes.blog.model.result.SuccessDataResult;
import com.gunes.blog.service.PostService;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<SuccessDataResult<Page<PostDto>>> getAllPosts(Pageable pageable) {
        Page<PostDto> postResponses = postService.getAll(pageable).map(Mapper::convertToPostResponseFrom);
        return ResponseEntity.ok(new SuccessDataResult<>(postResponses));
    }

    @Operation(summary = "returns a certain post by its id")
    @GetMapping("/{id}")
    public ResponseEntity<SuccessDataResult<PostDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(Mapper.convertToPostResponseFrom(postService.getById(id))));
    }

    @Operation(summary = "creates a post and adds its to the database then returns post's path")
    @PostMapping
    public ResponseEntity<SuccessDataResult<PostDto>> createPost(@Valid @RequestBody CreatePostDto createPostDto, Authentication auth) {
        PostDto response = Mapper.convertToPostResponseFrom(
                postService.create(createPostDto, auth.getName())
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(new SuccessDataResult<>(response));
    }

    @Operation(summary = "Returns the updated post")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessDataResult<PostDto>> updatePost(@Valid @PathVariable Long id, @RequestBody UpdatePostDto updatePostDto, Authentication auth) {
        PostDto response = Mapper.convertToPostResponseFrom(postService.updatePost(id, updatePostDto, auth));
        return ResponseEntity.ok(new SuccessDataResult<>(response));
    }

    @Operation(summary = "Doesn't return anything if the delete operation is successful")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication auth) {
        postService.deletePost(id, auth);
        return ResponseEntity.noContent().build();
    }
}
