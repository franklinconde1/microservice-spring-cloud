package com.spring.cloud.user.controller;

import com.spring.cloud.user.entity.User;
import com.spring.cloud.user.model.Bike;
import com.spring.cloud.user.model.Car;
import com.spring.cloud.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("user/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) throws Exception {
        User userId = userService.getUserById(id);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userId);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAll() throws Exception {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> getUserSave(@RequestBody User user) throws Exception {
        User userNew = userService.getUserSave(user);
        return ResponseEntity.ok(userNew);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable int userId) throws Exception {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCars")
    @PostMapping("savecar/{userId}")
    public ResponseEntity<Car> getSaveCar(@PathVariable int userId, @RequestBody Car car) throws Exception {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveCar(userId, car));
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("bikes/{userId}")
    public ResponseEntity<List<Bike>> getBike(@PathVariable int userId) throws Exception {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBikes")
    @PostMapping("savebike/{userId}")
    public ResponseEntity<Bike> getSaveBike(@PathVariable int userId, @RequestBody Bike bike) throws Exception {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId, bike));
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getVehiclesOfUser(@PathVariable int userId) {
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene los autos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCars(@PathVariable int userId, @RequestBody Car car, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene el auto en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBikes(@PathVariable int userId, @RequestBody Bike bike, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene la moto en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene los vehículos en el taller", HttpStatus.OK);
    }
}
