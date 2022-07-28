package com.spring.cloud.bike.service;

import com.spring.cloud.bike.entity.Bike;
import com.spring.cloud.bike.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

    public List<Bike> getAll() throws Exception {
        return bikeRepository.findAll();
    }

    public Bike getBikeSave(Bike bike) throws Exception{
        Bike bikeNew = bikeRepository.save(bike);
        return bikeNew;
    }

    public Bike getById(Integer id) throws Exception{
        return bikeRepository.findById(id).orElseThrow();
    }

    public List<Bike> findByUserId(int userId){
        return bikeRepository.findByUserId(userId);
    }
}