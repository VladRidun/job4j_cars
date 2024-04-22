package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.repository.ModelRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateModelService implements ModelService {

    private final ModelRepository modelRepository;

    @Override
    public Model create(Model model) {
        return modelRepository.create(model);
    }

    @Override
    public void update(Model model) {
        modelRepository.update(model);
    }

    @Override
    public void delete(Long modelId) {
        modelRepository.delete(modelId);
    }

    @Override
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    @Override
    public Optional<Model> findById(Long modelId) {
        return modelRepository.findById(modelId);
    }
}