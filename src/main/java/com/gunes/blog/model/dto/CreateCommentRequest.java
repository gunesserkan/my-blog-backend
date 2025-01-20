package com.gunes.blog.model.dto;

import com.gunes.blog.model.entity.Post;
import com.gunes.blog.model.entity.User;

import java.time.LocalDateTime;

public record CreateCommentRequest(
        String content
) {
}
