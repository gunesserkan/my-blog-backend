package com.gunes.blog.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDto(
        String name,
        String username,
        String email,
        List<PostDto> posts
) {
}
