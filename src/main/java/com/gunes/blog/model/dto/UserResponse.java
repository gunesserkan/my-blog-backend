package com.gunes.blog.model.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record UserResponse(
        String name,
        String username,
        String email,
        List<PostResponse> posts
){}
