package com.gunes.blog.mapper;

import com.gunes.blog.dto.PostResponse;
import com.gunes.blog.model.Post;

public class PostMapper {
    public static PostResponse convertToPostResponseFrom(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
