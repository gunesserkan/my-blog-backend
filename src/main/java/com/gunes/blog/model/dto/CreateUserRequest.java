package com.gunes.blog.model.dto;

import com.gunes.blog.model.enums.Role;
import lombok.Builder;

import java.util.Set;


@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        String email,
        Set<Role> authorities

) {
}
