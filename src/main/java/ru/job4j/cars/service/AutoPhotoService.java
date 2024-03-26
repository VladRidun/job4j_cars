package ru.job4j.cars.service;

import ru.job4j.cars.dto.AutoPhotoDto;
import ru.job4j.cars.model.AutoPhoto;

import java.util.Optional;

public interface AutoPhotoService {

    AutoPhoto save(AutoPhotoDto photoDto);

    Optional<AutoPhotoDto> getPhotoById(int id);

    void deleteById(int id);
}
