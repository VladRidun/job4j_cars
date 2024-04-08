package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.AutoPhotoDto;
import ru.job4j.cars.model.AutoPhoto;
import ru.job4j.cars.repository.AutoPhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class HibernateAutoPhotoService implements AutoPhotoService {
    private final AutoPhotoRepository photoRepository;

    private final String storageDirectory;

    public HibernateAutoPhotoService(AutoPhotoRepository photoRepository,
                                     @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = photoRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AutoPhoto save(AutoPhotoDto photoDto) {
        var path = getNewFilePath(photoDto.getName());
        writeFileBytes(path, photoDto.getContent());
        return photoRepository.save(new AutoPhoto(photoDto.getName(), path));
    }

    @Override
    public Optional<AutoPhotoDto> getPhotoById(int id) {
        var photoOptional = photoRepository.findById(id);
        if (photoOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(photoOptional.get().getPath());
        return Optional.of(new AutoPhotoDto(photoOptional.get().getName(), content));
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        var fileOptional = photoRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            photoRepository.deleteById(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
