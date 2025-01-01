package com.gunes.blog.controller;


import com.gunes.blog.dto.AuthRequest;
import com.gunes.blog.dto.CreateUserRequest;
import com.gunes.blog.dto.UserResponse;
import com.gunes.blog.mapper.Mapper;
import com.gunes.blog.model.User;
import com.gunes.blog.service.JwtService;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "register a user to the database")
    @PostMapping("/auth/register")
    public ResponseEntity<Void> addNewUser(@RequestBody CreateUserRequest request) {
        User savedUser = userService.createUser(request);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/api/v1/profiles/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();
        if (authentication.isAuthenticated()) {
            return ResponseEntity.created(location)
                    .header(HttpHeaders.AUTHORIZATION,
                            "Bearer " + jwtService.generateToken(authentication.getName())).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "authenticates user and returns a jwt token")
    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateToken(authentication.getName())).build();
        }
        throw new UsernameNotFoundException("Invalid username" + request.username());
    }

    @Operation(summary = "returns user's informations by id")
    @GetMapping("/profiles/{username}")
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
