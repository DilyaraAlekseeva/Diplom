package ru.alekseeva.photoGram.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String caption;
    private String location;
    private Integer likes;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    //fetch = FetchType.EAGER, чтобы сразу же видеть комментарий под постом
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public Post() {
    }

    public Post(Long id, String title, String caption, String location, Integer likes, Set<String> likedUsers, User user, List<Comment> comments, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.caption = caption;
        this.location = location;
        this.likes = likes;
        this.likedUsers = likedUsers;
        this.user = user;
        this.comments = comments;
        this.createdDate = createdDate;
    }

    @PrePersist
    protected void onCreate()
    {
        this.createdDate = LocalDateTime.now();
    }
}
