package com.example.hibernatereactive.repository;

import com.example.hibernatereactive.entity.Car;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class CarRepo {
    private static final Logger LOGGER = Logger.getLogger(CarRepo.class.getName());
    private final Mutiny.SessionFactory sessionFactory;

    public Uni<List<Car>> findAll() {
        return sessionFactory.withSession(session -> session.createQuery("from Car", Car.class)
                .getResultList());
    }

    public Uni<Car> findById(Integer id) {
        Objects.requireNonNull(id, "Car with ID \" + id + \" not found");
        return sessionFactory.withSession(session -> session.find(Car.class, id))
                .onItem()
                .ifNull()
                .failWith(() -> new RuntimeException(id.toString()));
    }

    public Uni<Car> save(Car car) {
        return sessionFactory.withTransaction((session, tx) -> session.persist(car)
                .chain(session::flush)
                .replaceWith(car));
    }

    public Uni<Car> update(Integer id, Car updatedCar) {
        return sessionFactory.withTransaction((session, tx) -> session.merge(updatedCar)
                .chain(session::flush)
                .replaceWith(updatedCar));
    }

    public Uni<Void> delete(Integer id) {
        return sessionFactory.withTransaction((session, tx) -> session.find(Car.class, id)
                .chain(car -> session.remove(car))
                .replaceWithVoid());
    }

    public Uni<List<Car>> findCarsWithKilowattGreaterThan(Integer threshold) {
        return sessionFactory.withSession(session -> session.createQuery("select c from Car c where c.kilowatt > :threshold", Car.class)
                .setParameter("threshold", threshold)
                .getResultList());
    }
}
