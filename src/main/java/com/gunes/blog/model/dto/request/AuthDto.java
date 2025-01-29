package com.gunes.blog.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthDto(
        @NotBlank(message = "Username connot be blank")
        @Size(min = 5, max = 30, message = "Username must be between 3 and 30 characters")
        String username,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        String password) {
}
