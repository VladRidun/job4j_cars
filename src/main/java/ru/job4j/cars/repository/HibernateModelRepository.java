package ru.job4j.cars.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateModelRepository implements ModelRepository {
    private final CrudRepository crudRepository;

    @Override
    public Model create(Model model) {
        crudRepository.run(session -> session.save(model));
        return model;
    }

    @Override
    public void update(Model model) {
        crudRepository.run(session -> session.merge(model));
    }

    @Override
    public void delete(Long modelId) {
        crudRepository.run(
                "delete from Model where id = :fId",
                Map.of("fId", modelId)
        );
    }

    @Override
    public List<Model> findAll() {
        return crudRepository.query(" from Model c order by c.id", Model.class);
    }

    @Override
    public Optional<Model> findById(Long modelId) {
        return crudRepository.optional("FROM Model WHERE id = :fId", Model.class,
                Map.of("fId", modelId)
        );
    }
}
