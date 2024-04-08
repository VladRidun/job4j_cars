package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int postId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    @Override
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post p join fetch p.car join fetch p.user join fetch p.autoPhoto p order by p.id", Post.class);
    }

    @Override
    public Optional<Post> findById(int taskId) {
        return crudRepository.optional("FROM Post p join fetch p.car WHERE p.id = :fId", Post.class,
                Map.of("fId", taskId)
        );
    }

    @Override
    public List<Post> getLastDayPosts() {
        return crudRepository.query(
                "select from p Post where p.created >= :fTime", Post.class,
                Map.of("fTime", Timestamp.valueOf(LocalDateTime.now().minusDays(1))
                ));
    }

    @Override
    public List<Post> getPostsWithPhoto() {
        return crudRepository.query(
                "select from p Post where p.autoPhotos.size != 0", Post.class);
    }

    @Override
    public List<Post> getPostsWithBrand(String brand) {
        return crudRepository.query(
                "select from p Post where p.car.brand = :fBrand", Post.class,
                Map.of("fBrand", brand)
        );
    }
}
