package com.tdd.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tdd.demo.domain.Car;
import com.tdd.demo.exception.CarNotFoundException;
import com.tdd.demo.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarController {

	private CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/{name}")
	public Car getCar(@PathVariable String name) {
		return carService.getCarDetails(name);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void carNotFoundHandler(CarNotFoundException ex) {
		// throws CarNotFoundException
	}

}
