package com.gunes.blog.model.dto;

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
