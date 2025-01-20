package com.gunes.blog.model.mapper;

import com.gunes.blog.model.dto.CommentResponse;
import com.gunes.blog.model.dto.PostResponse;
import com.gunes.blog.model.dto.UserResponse;
import com.gunes.blog.model.entity.Comment;
import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;

import java.util.stream.Collectors;

public class Mapper {
    public static PostResponse convertToPostResponseFrom(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static UserResponse convertToUserResponseFrom(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .posts(user.getPosts()
                        .stream()
                        .map(Mapper::convertToPostResponseFrom)
                        .collect(Collectors.toList()))
                .build();
    }

    public static CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .post(Mapper.convertToPostResponseFrom(comment.getPost()))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
