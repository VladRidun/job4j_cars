package ru.job4j.cars.mapper;

import org.mapstruct.Mapper;
import ru.job4j.cars.dto.AutoPhotoDto;
import ru.job4j.cars.model.AutoPhoto;

@Mapper(componentModel = "spring")
public interface AutoPhotoMapper {
    default AutoPhotoDto getModelFromEntityCustom(AutoPhoto autoPhoto, byte[] content) {
        AutoPhotoDto autoPhotoDto = new AutoPhotoDto();
        autoPhotoDto.setName(autoPhoto.getName());
        autoPhotoDto.setContent(content);
        return autoPhotoDto;
    }
}
