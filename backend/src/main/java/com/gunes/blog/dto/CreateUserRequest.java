package com.gunes.blog.dto;

import com.gunes.blog.model.Role;
import lombok.Builder;

import java.util.Set;


@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities

) {
}
