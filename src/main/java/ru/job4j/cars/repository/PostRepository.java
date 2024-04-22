package ru.job4j.cars.repository;


import ru.job4j.cars.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post create(Post post);

    void update(Post post);

    void delete(Long postId);

    List<Post> findAllOrderById();

    Optional<Post> findById(Long postId);

    List<Post> getLastDayPosts();

    List<Post> getPostsWithPhoto();

    List<Post> getPostsWithBrand(String brand);
}
