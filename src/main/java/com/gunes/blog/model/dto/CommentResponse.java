package com.gunes.blog.model.dto;

import com.gunes.blog.model.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Long id,
        String content,
        String username,
        PostResponse post,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
