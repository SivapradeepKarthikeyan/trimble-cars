package com.trimble.trimblecars.repositories;

import com.trimble.trimblecars.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,String> {
    List<Car> findByStatus(String status);
}
