package ru.job4j.cars.service;

import ru.job4j.cars.model.Model;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    Model create(Model model);

    void update(Model model);

    void delete(Long modelId);

    List<Model> findAll();

    Optional<Model> findById(Long modelId);
}
