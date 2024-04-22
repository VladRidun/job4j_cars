package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateBrandService implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.create(brand);
    }

    @Override
    public void update(Brand brand) {
        brandRepository.update(brand);
    }

    @Override
    public void delete(Long brandId) {
        brandRepository.delete(brandId);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(Long brandId) {
        return brandRepository.findById(brandId);
    }
}
