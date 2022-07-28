package com.spring.cloud.car.controller;

import com.spring.cloud.car.entity.Car;
import com.spring.cloud.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("car/{id}")
    public ResponseEntity<Car> getById(@PathVariable Integer id) throws Exception {
        Car carId = carService.getById(id);
        if (carId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carId);
    }

    @GetMapping("cars")
    public ResponseEntity<List<Car>> getAll() throws Exception {
        List<Car> users = carService.getAll();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

        @PostMapping
        public ResponseEntity<Car> getUserSave(@RequestBody Car car) throws Exception {
         Car carNew = carService.getCarSave(car);
         return ResponseEntity.ok(carNew);
    }

    @GetMapping("carsUser/{userId}")
    public ResponseEntity<List<Car>> findByUserId(@PathVariable int userId) {
        List<Car> cars = carService.findByUserId(userId);
        return ResponseEntity.ok(cars);
    }
}
