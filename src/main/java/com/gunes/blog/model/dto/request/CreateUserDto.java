package com.gunes.blog.model.dto.request;

import com.gunes.blog.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;


@Builder
public record CreateUserDto(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,
        @NotBlank(message = "Username connot be blank")
        @Size(min = 5, max = 30, message = "Username must be between 3 and 30 characters")
        String username,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        String password,
        @NotBlank(message = "email cannot be blank")
        @Size(min = 11, max = 50, message = "Email must be between 11 and 50 characters")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Authorities cannot be blank")
        @Size(min = 1, max = 3, message = "Authorities must be between 1 and 3")
        Set<Role> authorities

) {
}
