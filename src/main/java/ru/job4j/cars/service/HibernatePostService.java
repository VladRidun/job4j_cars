package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernatePostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post create(Post post) {
        return postRepository.create(post);
    }

    @Override
    public void update(Post post) {
        postRepository.update(post);
    }

    @Override
    public void delete(int postId) {
        postRepository.delete(postId);
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public Optional<Post> findById(int taskId) {
        return Optional.empty();
    }

    @Override
    public List<Post> getLastDayPosts() {
        return null;
    }

    @Override
    public List<Post> getPostsWithPhoto() {
        return null;
    }

    @Override
    public List<Post> getPostsWithBrand(String brand) {
        return null;
    }
}
