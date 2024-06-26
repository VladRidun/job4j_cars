package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> create(User user);

    void update(User user);

    void delete(Long userId);

    List<User> findAllOrderById();

    Optional<User> findById(Long userId);

    List<User> findByLikeLogin(String key);

    Optional<User> findByLoginAndPassword(String login, String password);

}
