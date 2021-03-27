package com.tdd.demo.service;

import com.tdd.demo.domain.Car;
import com.tdd.demo.repository.CarRepository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

  @Mock
  private CarRepository carRepository;

  private CarService carService;

  @Before
  public void setUp() throws Exception {
    this.carService = new CarService(carRepository);
  }

  @Test
  public void getCarDetails_returnsCarInfo() {
    BDDMockito.given(carRepository.findByName("prius")).willReturn(new Car("prius", "hybrid"));

    Car car = carService.getCarDetails("prius");

    Assertions.assertThat(car.getName()).isEqualTo("prius");
    Assertions.assertThat(car.getType()).isEqualTo("hybrid");
  }
}
