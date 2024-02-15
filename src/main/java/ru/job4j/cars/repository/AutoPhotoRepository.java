package ru.job4j.cars.repository;

import ru.job4j.cars.model.AutoPhoto;

import java.util.Optional;

public interface AutoPhotoRepository {
    AutoPhoto save(AutoPhoto file);

    Optional<AutoPhoto> findById(int id);

    void deleteById(int id);
}
