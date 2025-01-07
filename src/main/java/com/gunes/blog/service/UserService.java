package com.gunes.blog.service;


import com.gunes.blog.dto.CreateUserRequest;
import com.gunes.blog.dto.UpdateUserRequest;
import com.gunes.blog.dto.UserResponse;
import com.gunes.blog.exception.UserNotFoundException;
import com.gunes.blog.exception.UsernameAlreadyExistsException;
import com.gunes.blog.mapper.Mapper;
import com.gunes.blog.model.User;
import com.gunes.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException(username));
    }

    public User updateUser(UpdateUserRequest request, String username) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        existingUser.setName(request.name());
        existingUser.setEmail(request.email());
        existingUser.setAuthorities(request.authorities());
        return userRepository.save(existingUser);
    }

    public User createUser(CreateUserRequest request) {
        if (isUsernameExists(request.username())) {
            throw new UsernameAlreadyExistsException("Username already exists" + request.username());
        }
        User newUser = User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnable(true)
                .accountNonLocked(true)
                .build();

        return userRepository.save(newUser);
    }

    public Boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
