package com.spring.cloud.car.service;

import com.spring.cloud.car.entity.Car;
import com.spring.cloud.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() throws Exception {
        return carRepository.findAll();
    }

    public Car getCarSave(Car car) throws Exception{
        Car carNew = carRepository.save(car);
        return carNew;
    }

    public Car getById(Integer id) throws Exception{
        return carRepository.findById(id).orElseThrow();
    }

    public List<Car> findByUserId(int userId){
        return carRepository.findByUserId(userId);
    }
}
