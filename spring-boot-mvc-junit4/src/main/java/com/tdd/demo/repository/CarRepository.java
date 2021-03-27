package com.tdd.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdd.demo.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	Car findByName(String name);
}
