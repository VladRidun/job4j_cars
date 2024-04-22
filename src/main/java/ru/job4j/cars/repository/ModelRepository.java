package ru.job4j.cars.repository;

import ru.job4j.cars.model.Model;

import java.util.List;
import java.util.Optional;

public interface ModelRepository {
    Model create(Model model);

    void update(Model model);

    void delete(Long modelId);

    List<Model> findAll();

    Optional<Model> findById(Long modelId);
}
