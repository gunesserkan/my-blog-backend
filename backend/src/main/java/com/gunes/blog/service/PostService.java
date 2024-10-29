package com.gunes.blog.service;

import com.gunes.blog.model.Post;
import com.gunes.blog.model.User;
import com.gunes.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    public Post getById(Long id) {
        return postRepository.getReferenceById(id);
    }
    public Page<Post> getAll(Pageable pageable) {
    Page<Post>page=postRepository.findAll(PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.ASC, "createdAt"))
    ));

    return page;
    }

    public Post create(Post post) {
       return postRepository.save(post);
    }
}
