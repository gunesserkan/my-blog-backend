package com.gunes.blog.model.dto;

import lombok.Builder;

@Builder
public record AuthRequest(String username, String password) {
}
