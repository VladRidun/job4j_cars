package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateAutoPhotoRepositoryTest {
    private static SessionFactory sf;
    private static HibernateAutoPhotoRepository photoRepository;

    @BeforeEach
    public void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        photoRepository = new HibernateAutoPhotoRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clean() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete AutoPhoto")
                    .executeUpdate();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }
}