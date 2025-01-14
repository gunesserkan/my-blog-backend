package com.gunes.blog.model.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long id,
        String username
) {
}
