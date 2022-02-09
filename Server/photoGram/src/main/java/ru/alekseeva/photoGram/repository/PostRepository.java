package ru.alekseeva.photoGram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alekseeva.photoGram.entity.Post;
import ru.alekseeva.photoGram.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //найти все посты пользователя и отсортировать по дате создания (в профиле пользователя)
    List<Post> findAllByUserOrderByCreatedDateDesc(User user);

    //найти все посты, отсортированные по дате (на главной странице)
    List<Post> findAllByOrderByCreatedDateDesc();

    Optional<Post> findPostByIdAndUser(Long id, User user);

}