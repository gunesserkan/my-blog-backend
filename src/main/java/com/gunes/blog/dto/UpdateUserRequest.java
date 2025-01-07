package com.gunes.blog.dto;

import com.gunes.blog.model.Role;

import java.util.Set;

public record UpdateUserRequest(
        String name,
        String email,
        Set<Role> authorities
) {
}
