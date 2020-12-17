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

import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;
import com.example.demo.model.DrivingDto;
import com.example.demo.model.PassengerCarDto;
import com.example.demo.model.TurnDto;

@Controller
@RequestMapping("/buses")
public class BusesController {

	private RestTemplate rest = new RestTemplate();
	
	@GetMapping
	public String busesView(Model model) {
		List<BusesDto> buses = Arrays.asList(rest.getForObject("http://localhost:8080/buses", BusesDto[].class));
		model.addAttribute("buses", buses);
		return "buses-view";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteBuses(@PathVariable("id") String id, Model model) {
		rest.delete("http://localhost:8080/buses/" + id);
		return busesView(model);
	}
	
	@GetMapping("/search")
	public String searchBuses(@PathParam("id") String id, Model model) {
		BusesDto buses = rest.getForObject("http://localhost:8080/buses/" + id, BusesDto.class);
		List<BusesDto> busesList = new ArrayList<>();
		busesList.add(buses);
		model.addAttribute("buses" , busesList);
		return "buses-view";
	}
	
	@GetMapping("/detail-by-turn/{id}")
	public String busesDetailByTurn(@PathVariable("id")String id ,  Model model) {
		TurnDto turnDto = rest.getForObject("http://localhost:8080/turn/" + id, TurnDto.class);
		model.addAttribute("buses" , turnDto.getBusesList());
		return "buses-view";
	}
	
	@GetMapping("/detail-by-passenger-car/{id}")
	public String busesDetailByPassengerCar(@PathVariable("id") String id , Model model) {
		PassengerCarDto passengerCarDto = rest.getForObject("http://localhost:8080/passenger-car/" + id, PassengerCarDto.class);
		model.addAttribute("buses" , passengerCarDto.getBusesList());
		return "buses-view";
	}
	
	@GetMapping("/detail-by-driver-id/{id}")
	public String busesDetailByDriverId(@PathVariable("id") String id , Model model) {
		List<BusesDto> busesList = Arrays.asList(rest.getForObject("http://localhost:8080/driver/buses-list-by-driver-id?driverId=" + id, BusesDto[].class));
		model.addAttribute("buses" , busesList);
		return "buses-view";
	}
	
}
