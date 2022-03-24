package com.tdd.demo.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tdd.demo.domain.Car;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CarRepositoryTest {

	@Autowired
	private CarRepository repository;

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void getCar_returnsCarDetails() {
		Car savedCar = entityManager.persistFlushFind(new Car("prius", "hybrid"));
		Car car = repository.findByName("prius");

		Assertions.assertThat(car.getName()).isEqualTo(savedCar.getName());
		Assertions.assertThat(car.getType()).isEqualTo(savedCar.getType());
	}
}
