package ru.job4j.cars.repository;


import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    Brand create(Brand brand);

    void update(Brand brand);

    void delete(Long brandId);

    List<Brand> findAll();

    Optional<Brand> findById(Long brandId);
}
