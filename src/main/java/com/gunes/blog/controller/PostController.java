package com.gunes.blog.controller;


import com.gunes.blog.dto.CreatePostRequest;
import com.gunes.blog.dto.PostResponse;
import com.gunes.blog.mapper.PostMapper;
import com.gunes.blog.model.Post;
import com.gunes.blog.model.User;
import com.gunes.blog.service.PostService;
import com.gunes.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts=new ArrayList<>();
        for(Post post:postService.getAll()){
            posts.add(PostMapper.convertToPostResponseFrom(post));
        }
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(PostMapper.convertToPostResponseFrom(postService.getById(id)));
    }
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
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }
}
