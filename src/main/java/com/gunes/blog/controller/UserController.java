package com.gunes.blog.controller;


import com.gunes.blog.model.dto.UpdateUserRequest;
import com.gunes.blog.model.dto.UserResponse;
import com.gunes.blog.model.mapper.Mapper;
import com.gunes.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(Mapper.convertToUserResponseFrom(userService.getByUsername(username)));
    }

    @Operation(summary = "returns updated user informations")
    @PutMapping("/{username}")
    @PreAuthorize("#username==authentication.name or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("username") String username, @RequestBody UpdateUserRequest updatedUser) {
        return ResponseEntity.ok(Mapper.convertToUserResponseFrom(userService.updateUser(updatedUser, username)));
    }
    @Operation(summary = "removes user from db by username")
    @DeleteMapping("/{username}")
    @PreAuthorize("#username==authentication.name or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
