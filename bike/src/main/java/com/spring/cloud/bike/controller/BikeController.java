package com.spring.cloud.bike.controller;

import com.spring.cloud.bike.entity.Bike;
import com.spring.cloud.bike.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bike")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;
    @GetMapping("bikes")
    public ResponseEntity<List<Bike>> getAll() throws Exception {
        List<Bike> users = bikeService.getAll();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Bike> getUserSave(@RequestBody Bike bike) throws Exception {
        Bike bikeNew = bikeService.getBikeSave(bike);
        return ResponseEntity.ok(bikeNew);
    }

    @GetMapping("bike/{id}")
    public ResponseEntity<Bike> getById(@PathVariable Integer id) throws Exception {
        Bike bikeId = bikeService.getById(id);
        if (bikeId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bikeId);
    }

    @GetMapping("bikesUser/{userId}")
    public ResponseEntity<List<Bike>> findByUserId(@PathVariable int userId) {
        List<Bike> bikes = bikeService.findByUserId(userId);
        return ResponseEntity.ok(bikes);
    }
}