package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateBrandRepository implements BrandRepository {
    private final CrudRepository crudRepository;

    @Override
    public Brand create(Brand brand) {
        crudRepository.run(session -> session.save(brand));
        return brand;
    }

    @Override
    public void update(Brand brand) {
        crudRepository.run(session -> session.merge(brand));
    }

    @Override
    public void delete(Long brandId) {
        crudRepository.run(
                "delete from Brand where id = :fId",
                Map.of("fId", brandId)
        );
    }

    @Override
    public List<Brand> findAll() {
        return crudRepository.query(" from Brand c order by c.id", Brand.class);
    }

    @Override
    public Optional<Brand> findById(Long brandId) {
        return crudRepository.optional("FROM Brand WHERE id = :fId", Brand.class,
                Map.of("fId", brandId)
        );
    }
}
