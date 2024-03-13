package com.example.hibernatereactive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Entity
@Table(name = "CAR", schema = "REACTIVE_SPRING_TEST")
public class Car {
    @Id
    @GeneratedValue
    Integer id;
    String brand;
    Integer kilowatt;
}
