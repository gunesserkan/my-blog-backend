package com.gunes.blog.model.dto;

public record UpdatePostRequest(
        String title,
        String content) {
}
