package com.gunes.blog.model.mapper;

import com.gunes.blog.model.dto.response.CommentDto;
import com.gunes.blog.model.dto.response.PostDto;
import com.gunes.blog.model.dto.response.UserDto;
import com.gunes.blog.model.entity.Comment;
import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;

import java.util.stream.Collectors;

public class Mapper {
    public static PostDto convertToPostResponseFrom(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static UserDto convertToUserResponseFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .posts(user.getPosts()
                        .stream()
                        .map(Mapper::convertToPostResponseFrom)
                        .collect(Collectors.toList()))
                .build();
    }

    public static CommentDto toCommentResponse(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .post(Mapper.convertToPostResponseFrom(comment.getPost()))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
