package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    final CrudRepository crudRepository;

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
