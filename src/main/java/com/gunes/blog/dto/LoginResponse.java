package com.gunes.blog.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long id,
        String username
) {
}
