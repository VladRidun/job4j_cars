package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateCarService implements CarService {
    private CarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }

    @Override
    public void delete(int carId) {
        carRepository.delete(carId);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(int carId) {
        return carRepository.findById(carId);
    }
}
