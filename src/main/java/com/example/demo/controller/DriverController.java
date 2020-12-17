package com.example.demo.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;

@Controller
@RequestMapping("/driver")
public class DriverController {
	
	private RestTemplate rest = new RestTemplate();
	
	@GetMapping
	public String driverView(Model model) {
		List<DriverDto> drivers = Arrays.asList(rest.getForObject("http://localhost:8080/driver", DriverDto[].class));
		model.addAttribute("drivers", drivers);
		return "driver-view";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteDriver(@PathVariable("id") String id, Model model) {
		rest.delete("http://localhost:8080/driver/" + id);
		return driverView(model);
	}
	
	@GetMapping("/search")
	public String searchDriver(@PathParam("key") String key, Model model) {
		List<DriverDto> drivers = Arrays.asList(rest.getForObject("http://localhost:8080/driver/search/" + key, DriverDto[].class));
		model.addAttribute("drivers" , drivers);
		return "driver-view";
	}
	
	@GetMapping("/edit/{id}")
	public String editDriverView(@PathVariable("id") String id, Model model) {
		DriverDto driver = rest.getForObject("http://localhost:8080/driver/" + id, DriverDto.class);
		model.addAttribute("driver", driver);
		return "edit-driver";
	}
	
	@PostMapping("/add")
	public String addDriverView() {
		return "add-driver";
	}
	
	@GetMapping("/detail-by-buses-id/{id}")
	public String driverDetailByBusesId(@PathVariable("id") String id , Model model) {
		List<DriverDto> driverList = Arrays.asList(rest.getForObject("http://localhost:8080/buses/driver-list-by-buses-id?busesId=" + id, DriverDto[].class));
		model.addAttribute("drivers" , driverList);
		return "driver-view";
	}
	
	@PostMapping("/edit")
	public String editDriver(@RequestParam("id") String id, @RequestParam("name")String name ,
			@RequestParam("identification")String identification , @RequestParam("lisenseId")String lisenseId,
			@RequestParam("lisenseType")String lisenseType ,@RequestParam("address") String address , 
			@RequestParam("dob")Date dob , @RequestParam("experience")int experience ,@RequestParam("isDriver") int isDriver , Model model) {
		DriverDto driver = rest.getForObject("http://localhost:8080/driver/" + id, DriverDto.class );
		driver.setAddress(address);
		driver.setDob(dob);
		driver.setExperience(experience);
		driver.setIdentification(identification);
		driver.setIsDriver(isDriver);
		driver.setLisenseId(lisenseId);
		driver.setLisenseType(lisenseType);
		driver.setName(name);
		
		rest.put("http://localhost:8080/driver/" + id, driver);
		return driverView(model);
	}
		
}
