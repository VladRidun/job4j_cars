package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.AutoPhoto;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAutoPhotoRepository implements AutoPhotoRepository {
    private final CrudRepository crudRepository;

    @Override
    public AutoPhoto save(AutoPhoto file) {
        crudRepository.run(session -> session.save(file));
        return file;
    }

    @Override
    public Optional<AutoPhoto> findById(Long id) {
        return crudRepository.optional("FROM AutoPhoto WHERE id = :fId", AutoPhoto.class,
                Map.of("fId", id)
        );
    }

    @Override
    public void deleteById(Long id) {
        crudRepository.run("delete from AutoFoto where id = :fId",
                Map.of("fId", id)
        );
    }
}