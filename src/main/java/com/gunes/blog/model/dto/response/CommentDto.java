package com.gunes.blog.model.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDto(
        Long id,
        String content,
        String username,
        PostDto post,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
