package com.gunes.blog.service;

import com.gunes.blog.model.dto.request.CreatePostDto;
import com.gunes.blog.model.dto.request.UpdatePostDto;
import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;
import com.gunes.blog.model.enums.Role;
import com.gunes.blog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Post> getAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "createdAt"))
        ));

        return page;
    }

    public Post create(CreatePostDto createPostDto, String userName) {
        User user = userService.getByUsername(userName);
        Post post = Post.builder()
                .title(createPostDto.title())
                .content(createPostDto.content())
                .createdAt(LocalDateTime.now())
                .user(user).build();
        return postRepository.save(post);
    }

    public Post updatePost(Long id, UpdatePostDto updatePostDto, Authentication auth) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!post.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not allowed to update this post");
        }
        post.setTitle(updatePostDto.title());
        post.setContent(updatePostDto.content());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Long id, Authentication auth) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!post.getUser().getUsername().equals(auth.getName()) && !auth.getAuthorities().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not allowed to update this post");
        }
        postRepository.delete(post);
    }
}
