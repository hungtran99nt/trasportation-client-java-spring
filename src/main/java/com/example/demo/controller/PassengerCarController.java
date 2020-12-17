package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.DriverDto;
import com.example.demo.model.PassengerCarDto;
import com.example.demo.model.TurnDto;

@Controller
@RequestMapping("/passenger-car")
public class PassengerCarController {

	
	private RestTemplate rest = new RestTemplate();
	
	@GetMapping
	public String passengerCarView(Model model) {
		List<PassengerCarDto> passengerCarList = Arrays.asList(rest.getForObject("http://localhost:8080/passenger-car", PassengerCarDto[].class));
		model.addAttribute("passengerCars", passengerCarList);
		return "passenger-car-view";
	}
	
	@PostMapping("/delete/{id}")
	public String deletePassengerCar(@PathVariable("id") String id, Model model) {
		rest.delete("http://localhost:8080/passenger-car/" + id);
		return passengerCarView(model);
	}
	
	@GetMapping("/search")
	public String searchPassengerCar(@PathParam("id") String id, Model model) {
		PassengerCarDto passengerCarDto = rest.getForObject("http://localhost:8080/passenger-car/search/" + id, PassengerCarDto.class);
		List<PassengerCarDto> passengerCarList = new ArrayList<>();
		passengerCarList.add(passengerCarDto);
		model.addAttribute("passengerCars" , passengerCarList);
		return "passenger-car-view";
	}
	
	@GetMapping("edit/{id}")
	public String editPassengerCarView(@PathVariable("id") String id, Model model) {
		PassengerCarDto passengerCar = rest.getForObject("http://localhost:8080/passenger-car/" + id, PassengerCarDto.class);
		model.addAttribute("passengerCar", passengerCar);
		return "edit-passenger-car";
	}
}
