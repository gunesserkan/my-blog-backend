package com.gunes.blog.repository;

import com.gunes.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PagingAndSortingRepository<Post,Long> {
    public Page<Post> findAll(Pageable pageable);
    public List<Post> findPostByUserName(String username);
}
