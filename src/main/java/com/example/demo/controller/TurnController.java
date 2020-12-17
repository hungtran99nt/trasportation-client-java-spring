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
import com.example.demo.model.TurnDto;

@Controller
@RequestMapping("/turn")
public class TurnController {

	private RestTemplate rest = new RestTemplate();
	
	@GetMapping
	public String turnView(Model model) {
		List<TurnDto> turns = Arrays.asList(rest.getForObject("http://localhost:8080/turn", TurnDto[].class));
		model.addAttribute("turns", turns);
		return "turn-view";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteDriver(@PathVariable("id") String id, Model model) {
		rest.delete("http://localhost:8080/turn/" + id);
		return turnView(model);
	}
	
	
	@GetMapping("/search")
	public String searchTurn(@PathParam("id") String id, Model model) {
		TurnDto turn = rest.getForObject("http://localhost:8080/turn/" + id, TurnDto.class);
		List<TurnDto> turnList = new ArrayList<>();
		turnList.add(turn);
		model.addAttribute("turns" , turnList);
		return "turn-view";
	}
	
	
}
