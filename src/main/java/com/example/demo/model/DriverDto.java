package com.example.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
	private int id;
	private String name;
	private String identification;
	private String lisenseId;
	private String lisenseType;
	private String address;
	private Date dob;
	private int experience;
	private int isDriver;
	private Iterable<DrivingDto> drivingList;
}
