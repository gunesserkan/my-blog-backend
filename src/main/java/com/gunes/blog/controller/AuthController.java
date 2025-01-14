package com.gunes.blog.controller;


import com.gunes.blog.model.dto.AuthRequest;
import com.gunes.blog.model.dto.CreateUserRequest;
import com.gunes.blog.model.dto.LoginResponse;
import com.gunes.blog.model.entity.User;
import com.gunes.blog.service.AuthService;
import com.gunes.blog.service.JwtService;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    public AuthController(JwtService jwtService, AuthService authService, UserService userService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "authenticates user and returns a jwt token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        User authenticatedUser = authService.authenticate(request.username(),request.password());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateToken(authenticatedUser.getUsername()))
                .body(LoginResponse.builder()
                        .id(authenticatedUser.getId())
                        .username(authenticatedUser.getUsername()).build());
    }

    @Operation(summary = "register a user to the database")
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> addNewUser(@RequestBody CreateUserRequest request) {
        User savedUser = userService.createUser(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/api/v1/profiles/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();
        User authenticatedUser = authService.authenticate(request.username(),request.password());
        return ResponseEntity.created(location)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateToken(authenticatedUser.getUsername()))
                .body(LoginResponse.builder()
                        .id(authenticatedUser.getId())
                        .username(authenticatedUser.getUsername()).build());
    }
}
