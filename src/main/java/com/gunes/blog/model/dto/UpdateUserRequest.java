package com.gunes.blog.model.dto;

import com.gunes.blog.model.enums.Role;

import java.util.Set;

public record UpdateUserRequest(
        String name,
        String email,
        Set<Role> authorities
) {
}
