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
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarRepositoryTest {
    private static SessionFactory sf;
    private static HibernateCarRepository carRepository;
    private static HibernateBrandRepository brandRepository;
    private static HibernateModelRepository modelRepository;

    @BeforeEach
    public void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        brandRepository = new HibernateBrandRepository(new CrudRepository(sf));
        modelRepository = new HibernateModelRepository(new CrudRepository(sf));
        carRepository = new HibernateCarRepository(new CrudRepository(sf));
        brandRepository.create(new Brand(1L, "Renault"));
        modelRepository.create(new Model(1L, "Duster", 1));
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
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenCreateThenGetSameCarWithBrand() {
        var car = new Car(1L,
                brandRepository.findById(1L).get(),
                modelRepository.findById(1L).get(),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null);
        var carAdded = carRepository.create(car);
        assertThat(carAdded.getBrand()).isEqualTo(car.getBrand());
    }

    @Test
    void whenUpdateIsDone() {
        var car1 = new Car(1L,
                brandRepository.findById(1L).get(),
                modelRepository.findById(1L).get(),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null);
        var carAdded = carRepository.create(car1);
        var car2 = new Car(
                1L,
                new Brand(1L, "Renault"),
                new Model(1L, "Duster", 1),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null);
        carRepository.update(car2);
        var carUpdate = carRepository.findById(carAdded.getId());
        assertThat(carUpdate.get().getModel()).isEqualTo(car2.getModel());
    }

    @Test
    void whenDeleteIsDone() {
        var car1 = new Car(
                1L,
                new Brand(1L, "Renault"),
                new Model(1L, "Duster", 1),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null);
        var carAdded = carRepository.create(car1);
        carRepository.delete(carAdded.getId());
        carRepository.findById(carAdded.getId());
        assertThat(carRepository.findById(carAdded.getId())).isEmpty();
    }

    @Test
    void whenFindAllIsDone() {
        var car1 = new Car(
                1L,
                new Brand(1L, "Renault"),
                new Model(1L, "Duster", 1),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null);

        var car2 = new Car(
                1L,
                new Brand(1L, "Renault"),
                new Model(1L, "Duster", 1),
                "Vin123",
                111111L,
                2016,
                null,
                null,
                null,
                null);
        var carAdded1 = carRepository.create(car1);
        var carAdded2 = carRepository.create(car2);
        var carsAdded = carRepository.findAll();
        List<String> carsAddedVinsNumbers = new ArrayList<>();
        for (Car car : carsAdded) {
            carsAddedVinsNumbers.add(car.getVinNumber());
        }
        assertThat(List.of(car1.getVinNumber(), car2.getVinNumber())).isEqualTo(carsAddedVinsNumbers);
    }
}