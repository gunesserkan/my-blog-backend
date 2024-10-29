package com.gunes.blog.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record PostResponse(Long id,String title, String content,String username, LocalDateTime createdAt) {
}
