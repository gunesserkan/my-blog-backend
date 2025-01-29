package com.gunes.blog.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentDto(
        @NotBlank(message = "Content cannot be blank")
        @Size(min = 2, max = 200, message = "Content must be between 2 and 200 characters")
        String content
) {
}
