package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {
    private final CrudRepository crudRepository;

    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    @Override
    public void delete(Long carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }

    @Override
    public List<Car> findAll() {
        return crudRepository.query(" from Car c order by c.id", Car.class);
    }

    @Override
    public Optional<Car> findById(Long carId) {
        return crudRepository.optional("FROM Car WHERE id = :fId", Car.class,
                Map.of("fId", carId)
        );
    }
}