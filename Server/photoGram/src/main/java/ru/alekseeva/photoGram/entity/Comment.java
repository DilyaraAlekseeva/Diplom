package ru.alekseeva.photoGram.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private Long userId;
    @Column(columnDefinition = "text", nullable = false)
    private String message;
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public Comment() {
    }

    public Comment(Long id, Post post, String username, Long userId, String message, LocalDateTime createdDate) {
        this.id = id;
        this.post = post;
        this.username = username;
        this.userId = userId;
        this.message = message;
        this.createdDate = createdDate;
    }

    @PrePersist
    protected void onCreate()
    {
        this.createdDate = LocalDateTime.now();
    }
}

