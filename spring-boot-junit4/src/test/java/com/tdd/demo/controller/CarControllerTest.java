package com.tdd.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tdd.demo.domain.Car;
import com.tdd.demo.exception.CarNotFoundException;
import com.tdd.demo.service.CarService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {
  
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CarService carService;

  @Test
  public void getCar_ShouldReturnCar() throws Exception {

    BDDMockito
      .given(carService.getCarDetails(ArgumentMatchers.anyString()))
      .willReturn(new Car("prius", "hybrid"));

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/prius"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("prius"))
      .andExpect(jsonPath("type").value("hybrid"));
  }

  @Test
  public void getCar_notFound() throws Exception {
    
    BDDMockito
      .given(carService.getCarDetails(ArgumentMatchers.anyString()))
      .willThrow(new CarNotFoundException());

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/prius"))
      .andExpect(status().isNotFound());

  }
}
