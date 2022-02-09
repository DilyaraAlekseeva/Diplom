package ru.alekseeva.photoGram.entity;

import ru.alekseeva.photoGram.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) //не может быть пусто
    private String name;
    @Column(unique = true, updatable = false) //имя пользователя уникально и его нельзя обновлять
    private String username;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text") //меняем тип данной колонки, чтобы сохранять объемный текст
    private String bio;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();

    //каскадное поведение - когда мы удалим пользователя, все его посты тоже будут удалены
    //Lazy - когда мы хотим получить данные пользователя, нам не нужно автоматически получать все его посты
    //orphanRemoval=true, при удалении поста, он удаляется из базы.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public User(String email, String name, String lastname, String username, String password) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String name, String username, String lastname, String email, String bio, String password, Set<ERole> roles, List<Post> posts, LocalDateTime createdDate, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.roles = roles;
        this.posts = posts;
        this.createdDate = createdDate;
        this.authorities = authorities;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    /**
     * SECURITY
     */

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

