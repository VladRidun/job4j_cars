package ru.job4j.cars.service;

import ru.job4j.cars.dto.AutoPhotoDto;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post create(Post post, AutoPhotoDto photoDto);

    void update(Post post);

    void update(Post post, AutoPhotoDto photoDto);

    void delete(int taskId);

    List<Post> findAllOrderById();

    Optional<Post> findById(int postId);

    List<Post> getLastDayPosts();

    List<Post> getPostsWithPhoto();

    List<Post> getPostsWithBrand(String brand);
}
