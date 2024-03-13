package com.example.hibernatereactive.controller;

import com.example.hibernatereactive.entity.Car;
import com.example.hibernatereactive.repository.CarRepo;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarRepo carRepo;

    @GetMapping
    public Uni<ResponseEntity<List<Car>>> getAllCars() {
        return carRepo.findAll()
                .map(cars -> ResponseEntity.ok().body(cars))
                .onFailure().recoverWithItem(th -> ResponseEntity.status(500).build());
    }

    @GetMapping("/{id}")
    public Uni<ResponseEntity<Car>> getCarById(@PathVariable Integer id) {
        return carRepo.findById(id)
                .map(car -> ResponseEntity.ok().body(car))
                .onFailure().recoverWithItem(th -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Uni<ResponseEntity<Car>> createCar(@RequestBody Car car) {
        return carRepo.save(car)
                .map(savedCar -> ResponseEntity.ok().body(savedCar))
                .onFailure().recoverWithItem(th -> ResponseEntity.status(500).build());
    }

    @PutMapping("/{id}")
    public Uni<ResponseEntity<Car>> updateCar(@PathVariable Integer id, @RequestBody Car updatedCar) {
        return carRepo.update(id, updatedCar)
                .map(car -> ResponseEntity.ok().body(car))
                .onFailure().recoverWithItem(th -> ResponseEntity.status(500).build());
    }

    @DeleteMapping("/{id}")
    public Uni<ResponseEntity<Object>> deleteCar(@PathVariable Integer id) {
        return carRepo.delete(id)
                .map(v -> ResponseEntity.ok().build())
                .onFailure().recoverWithItem(th -> ResponseEntity.status(500).build());
    }

    @GetMapping("/kilowatt/{threshold}")
    public Uni<ResponseEntity<List<Car>>> getCarsByKilowatt(@PathVariable Integer threshold) {
        return carRepo.findCarsWithKilowattGreaterThan(threshold)
                .map(cars -> ResponseEntity.ok().body(cars))
                .onFailure().recoverWithItem(th -> ResponseEntity.status(500).build());
    }
}
