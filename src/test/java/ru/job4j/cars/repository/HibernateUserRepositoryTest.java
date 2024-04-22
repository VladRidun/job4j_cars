package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HibernateUserRepositoryTest {
    private static SessionFactory sf;
    private static HibernateUserRepository userRepository;

    @BeforeEach
    public void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        userRepository = new HibernateUserRepository((new CrudRepository(sf)));
    }

    @AfterEach
    public void clean() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete User")
                    .executeUpdate();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenCreateAndGetSame() {
        var user = new User(1L, "Admin", "qwerty");
        var userAdded = userRepository.create(user);
        assertThat(userAdded.get().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenCreateAndFindByIdTrue() {
        var userAddedOpt = userRepository.create(new User(1L, "Admin", "qwerty"));
        var userFindOpt = userRepository.findById(1L);
        assertThat(userAddedOpt.get().getLogin()).isEqualTo(userFindOpt.get().getLogin());
    }

    @Test
    void whenUpdateAndGetSameUpdated() {
        var userAddedOpt = userRepository.create(new User(1L, "Admin", "qwerty"));
        userRepository.update(new User(1L, "User", "12345"));
        var userFindOpt = userRepository.findById(1L);
        assertThat(userFindOpt.get().getLogin()).isEqualTo("User");
    }

    @Test
    void whenDeleteIsTrue() {
        userRepository.create(new User(1L, "Admin", "qwerty"));
        var userFindOpt = userRepository.findById(1L);
        assertThat(userFindOpt.isEmpty());
    }

    @Test
    void findAllOrderById() {
        List<User> userListTest = List.of(
                new User(1L, "Admin", "qwerty"),
                new User(2L, "User", "12345"));
        userRepository.create(new User(1L, "Admin", "qwerty"));
        userRepository.create(new User(2L, "User", "12345"));
        List<User> usersListGet = userRepository.findAllOrderById();
        assertThat(userListTest).isEqualTo(usersListGet);
    }


    @Test
    void whenFindByLikeLoginTrueIsTrue() {
        userRepository.create(new User(1L, "Admin", "qwerty"));
        userRepository.create(new User(2L, "User", "12345"));
        userRepository.create(new User(3L, "User", "666666"));
       assertThat(userRepository.findByLikeLogin("User").size()).isEqualTo(2);
    }

    @Test
    void whenFindByLoginAndPasswordIsTrue() {
        userRepository.create(new User(1L, "Admin", "qwerty"));
        userRepository.create(new User(2L, "User", "12345"));
        userRepository.create(new User(3L, "User", "666666"));
        assertThat(userRepository.findByLoginAndPassword("Admin", "qwerty")
                .get().getPassword()).isEqualTo("qwerty");
    }
}