package com.gunes.blog.model.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDto(
        Long id,
        String title,
        String content,
        String username,
        LocalDateTime createdAt) {
}
