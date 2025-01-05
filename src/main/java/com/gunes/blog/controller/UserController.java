package com.gunes.blog.controller;


import com.gunes.blog.dto.UserResponse;
import com.gunes.blog.mapper.Mapper;
import com.gunes.blog.model.User;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "returns user's informations by username")
    @GetMapping("/{username}")
    @PreAuthorize("#username==authentication.name")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) {
        System.out.println("başarılı");
        Optional<User> requestedUser = userService.getByUsername(username);
        if (requestedUser.isPresent()) {
            return ResponseEntity.ok(Mapper.convertToUserResponseFrom(requestedUser.get()));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
