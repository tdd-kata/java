package com.tdd.demo.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tdd.demo.domain.Car;
import com.tdd.demo.service.CarService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@AutoConfigureCache
public class CachingTest {

	@Autowired
	private CarService carService;

	@MockBean
	private CarRepository carRepository;

	@Test
	public void caching() throws Exception {
		BDDMockito.given(carRepository.findByName(ArgumentMatchers.anyString())).willReturn(new Car("prius", "hybrid"));

		carService.getCarDetails("prius");
		carService.getCarDetails("prius");

		verify(carRepository, times(1)).findByName("prius");
	}
}
