package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Car create(Car car);

    void update(Car car);

    void delete(Long carId);

    List<Car> findAll();

    Optional<Car> findById(Long carId);
}
