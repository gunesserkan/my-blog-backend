package com.gunes.blog.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "summary")
    private String summary;
    @Column(name = "content")
    private String content;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name ="user_id")
    private User user;
}
