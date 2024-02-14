package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

public interface EngineRepository {
    Engine create(Engine engine);

    void update(Engine engine);

    void delete(int engineId);

    Optional<Engine> findById(int engineId);
}
