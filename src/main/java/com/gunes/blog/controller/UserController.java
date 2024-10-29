package com.gunes.blog.controller;


import com.gunes.blog.dto.AuthRequest;
import com.gunes.blog.dto.CreateUserRequest;
import com.gunes.blog.model.Post;
import com.gunes.blog.model.User;
import com.gunes.blog.service.JwtService;
import com.gunes.blog.service.PostService;
import com.gunes.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PostService postService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, PostService postService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.postService = postService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest request) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authentication.getName());
        }
        log.info("invalid username" + request.username());
        throw new UsernameNotFoundException("Invalid username" + request.username());
    }
}
