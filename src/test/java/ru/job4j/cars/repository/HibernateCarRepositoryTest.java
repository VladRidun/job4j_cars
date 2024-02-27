package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernateCarRepositoryTest {
    private static SessionFactory sf;
    private static HibernateCarRepository carRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        carRepository = new HibernateCarRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clean() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Car")
                    .executeUpdate();
            session.createQuery(
                            "delete Engine")
                    .executeUpdate();
            session.createQuery(
                            "delete Owner")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenCreateThenGetSameCarWithBrand() {
        var car = Car.builder()
                .brand("subaru")
                .model("outback")
                .vinNumber("Vin123")
                .colour("black")
                .bodyType("universal")
                .build();
        var carAdded = carRepository.create(car);
        assertThat(carAdded.getBrand()).isEqualTo(car.getBrand());
    }

    @Test
    void whenUpdateIsDone() {
        var car1 = Car.builder()
                .brand("subaru")
                .model("outback")
                .vinNumber("Vin123")
                .colour("black")
                .bodyType("universal")
                .build();
        var carAdded = carRepository.create(car1);
        var car2 = Car.builder()
                .id(carAdded.getId())
                .brand("subaru")
                .model("impreza")
                .vinNumber("Vin123")
                .colour("black")
                .bodyType("universal")
                .build();
        carRepository.update(car2);
        var carUpdate = carRepository.findById(carAdded.getId());
        assertThat(carUpdate.get().getModel()).isEqualTo(car2.getModel());
    }

    @Test
    void whenDeleteIsDone() {
        var car1 = Car.builder()
                .brand("subaru")
                .model("outback")
                .vinNumber("Vin123")
                .colour("black")
                .bodyType("universal")
                .build();
        var carAdded = carRepository.create(car1);
        carRepository.delete(carAdded.getId());
        carRepository.findById(carAdded.getId());
        assertThat(carRepository.findById(carAdded.getId())).isEmpty();
    }

    @Test
    void whenFindAllIsDone() {
        var car1 = Car.builder()
                .brand("subaru")
                .model("outback")
                .vinNumber("Vin123")
                .colour("black")
                .bodyType("universal")
                .build();

        var car2 = Car.builder()
                .brand("Citroen")
                .model("C4")
                .vinNumber("Vin2324")
                .colour("black")
                .bodyType("hatchback")
                .build();
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