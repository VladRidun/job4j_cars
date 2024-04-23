package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HibernatePostRepositoryTest {
    private static SessionFactory sf;
    private static HibernatePostRepository postRepository;
    private static HibernateCarRepository carRepository;
    private static HibernateBrandRepository brandRepository;
    private static HibernateModelRepository modelRepository;
    private static HibernateUserRepository userRepository;
    private static HibernateAutoPhotoRepository autoPhotoRepository;

    @BeforeEach
    public void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        postRepository = new HibernatePostRepository((new CrudRepository(sf)));
        brandRepository = new HibernateBrandRepository(new CrudRepository(sf));
        modelRepository = new HibernateModelRepository(new CrudRepository(sf));
        carRepository = new HibernateCarRepository(new CrudRepository(sf));
        userRepository = new HibernateUserRepository(new CrudRepository(sf));
        autoPhotoRepository = new HibernateAutoPhotoRepository(new CrudRepository(sf));
        brandRepository.create(new Brand(1L, "Renault"));
        modelRepository.create(new Model(1L, "Duster", 1));
        carRepository.create(new Car(1L,
                brandRepository.findById(1L).get(),
                modelRepository.findById(1L).get(),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null));
        userRepository.create(new User(1L, "Admin", "qwerty"));
        autoPhotoRepository.save(new AutoPhoto(1L, "test", "test_path"));
    }

    @AfterEach
    public void clean() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Brand")
                    .executeUpdate();
            session.createQuery(
                            "delete Model")
                    .executeUpdate();
            session.createQuery(
                            "delete Car")
                    .executeUpdate();
            session.createQuery(
                            "delete User")
                    .executeUpdate();
            session.createQuery(
                            "delete AutoPhoto")
                    .executeUpdate();
            session.createQuery(
                            "delete Post")
                    .executeUpdate();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenCreatePostAndGetSame() {
        var post = new Post(1L, "Prodam lada",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        var postAdded = postRepository.create(post);
        assertThat(post.getDescription()).isEqualTo(postAdded.getDescription());
    }

    @Test
    void whenFindByIdIsTrue() {
        var post = new Post(1L, "Prodam lada",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        var postAdded = postRepository.create(post);
        var postFoundOptional = postRepository.findById(postAdded.getId());
        assertThat(postAdded.getTitle()).isEqualTo(postFoundOptional.get().getTitle());
    }

    @Test
    void whenUpdateAndGetUpdated() {
        var post1 = new Post(1L, "Prodam lada",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        var post2 = new Post(1L, "Prodam Renault",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        postRepository.create(post1);
        postRepository.update(post2);
        var postOptional = postRepository.findById(1L);
        assertThat(postOptional.get().getTitle()).isEqualTo(post2.getTitle());
    }

    @Test
    void whenDeleteIsTrue() {
        var post1 = new Post(1L, "Prodam lada",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        postRepository.create(post1);
        postRepository.delete(1L);
        assertThat(postRepository.findById(1L)).isEmpty();
    }

    @Test
    void whenFindAllOrderById() {
        var post1 = new Post(1L, "Prodam lada",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        var post2 = new Post(1L, "Prodam Renault",
                "Otlichnoe sostoyanie",
                LocalDateTime.now(),
                10000,
                false,
                carRepository.findById(1L).get(),
                autoPhotoRepository.findById(1L).get(),
                userRepository.findById(1L).get());
        postRepository.create(post1);
        postRepository.create(post2);
        List<Post> postListAdd = List.of(post1, post2);
        List<Post> postListGet = postRepository.findAllOrderById();
        assertThat(postListAdd.containsAll(postListGet)).isTrue();
    }
}