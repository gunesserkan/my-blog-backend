package com.gunes.blog.model.dto.response;

import lombok.Builder;

@Builder
public record LoginDto(
        Long id,
        String username
) {
}
