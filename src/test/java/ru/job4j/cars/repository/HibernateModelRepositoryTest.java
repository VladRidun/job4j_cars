package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateModelRepositoryTest {

    private static SessionFactory sf;
    private static HibernateModelRepository modelRepository;
    private static HibernateBrandRepository brandRepository;

    @BeforeEach
    public void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();

        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        modelRepository = new HibernateModelRepository((new CrudRepository(sf)));
        brandRepository = new HibernateBrandRepository((new CrudRepository(sf)));
        brandRepository.create(new Brand(1L, "Lada"));
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

            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenCreateModelAndGetSame() {
        var model = new Model(1L, "Vesta", 1);
        var modelAdded = modelRepository.create(model);
        assertThat(modelAdded.getName()).isEqualTo(model.getName());
    }

    @Test
    void whenCreateAndFindByIdTrue() {
        var brandAdded = modelRepository.create(new Model(1L, "Granta", 1));
        var brandOptional = modelRepository.findById(1L);
        assertThat(brandOptional.get().getName()).isEqualTo(brandAdded.getName());
    }

    @Test
    void whenUpdateAndGetSameUpdated() {
        var model = new Model(1L, "Vesta", 1);
        var newModel = new Model(1L, "Granta", 2);
        modelRepository.create(model);
        modelRepository.update(newModel);
        var modelOptional = modelRepository.findById(1L);
        assertThat(modelOptional.get().getName()).isEqualTo(newModel.getName());
    }

    @Test
    void whenDeleteIsTrue() {
        var model = new Model(1L, "Vesta", 1);
        var modelAdded = modelRepository.create(model);
        modelRepository.delete(1L);
        var brandOptional = modelRepository.findById(1L);
        assertThat(brandOptional.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllIsTrue() {
        var granta = new Model(1L, "Granta", 1);
        var vesta = new Model(2L, "Vesta", 1);
        List<Model> brandListAdd = List.of(granta, vesta);
        modelRepository.create(granta);
        modelRepository.create(vesta);
        List<Model> brandListGet = modelRepository.findAll();
        assertThat(brandListAdd).isEqualTo(brandListGet);
    }
}