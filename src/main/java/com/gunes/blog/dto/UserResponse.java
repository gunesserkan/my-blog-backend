package com.gunes.blog.dto;

import com.gunes.blog.model.Post;
import lombok.Builder;

import java.util.List;
@Builder
public record UserResponse(
        String name,
        String username,
        String email,
        List<PostResponse> posts
){}
