package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    final CrudRepository crudRepository;

    @Override
    public List<Post> getLastDayPosts() {
        return crudRepository.query(
                "from p Post where p.created = localtimestamp", Post.class);
    }

    @Override
    public List<Post> getPostsWithPhoto() {
        return crudRepository.query(
                "from p Post where p.autoPhoto IS NOT NULL", Post.class);
    }

    @Override
    public List<Post> getPostsWithBrand(String brand) {
        return crudRepository.query(
                "from p Post where brand = :fBrand", Post.class,
                Map.of("fBrand", brand)
        );
    }
}
