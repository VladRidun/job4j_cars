package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        Optional<User> result = findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (!result.isEmpty()) {
            return Optional.empty();
        }
        crudRepository.run(session -> session.save(user));
        return Optional.ofNullable(user);
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(Long userId) {
        crudRepository.run("delete from User WHERE id = :fId", Map.of("fId", userId));
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query("from User ORDER BY id", User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(Long userId) {
        return crudRepository.optional("FROM User  WHERE id = :fId", User.class, Map.of("fId", userId));
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query("from User where login LIKE :fKey ", User.class, Map.of("fKey", "%" + key + "%"));
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional("from User WHERE login  = :FLogin and password = :FPassword", User.class, Map.of("FLogin", login, "FPassword", password));
    }
}