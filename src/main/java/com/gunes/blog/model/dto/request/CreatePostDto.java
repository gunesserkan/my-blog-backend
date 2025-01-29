package com.gunes.blog.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreatePostDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
        String title,
        @NotBlank(message = "Content cannot be blank")
        @Size(min = 10, max = 5000, message = "Content must be between 10 and 500 characters")
        String content) {
}
