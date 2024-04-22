package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.service.AutoPhotoService;

@Controller
@AllArgsConstructor
@RequestMapping("/photos")
public class AutoPhotoController {

    private final AutoPhotoService photoService;

    @GetMapping("/{photoId}")
    public ResponseEntity<?> getById(@PathVariable Long photoId) {
        var contentOptional = photoService.getPhotoById(photoId);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }
}