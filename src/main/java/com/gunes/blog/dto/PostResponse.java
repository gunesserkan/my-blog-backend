package com.gunes.blog.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record PostResponse(String title, String content,String username, LocalDateTime createdAt) {
}
