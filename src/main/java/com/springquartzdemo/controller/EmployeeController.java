package com.springquartzdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springquartzdemo.dto.EmployeeDTO;
import com.springquartzdemo.service.EmployeeServiceManager;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeServiceManager employeeServiceManager;
	
	@PostMapping("/employee")
	private void addEmployee(@RequestBody EmployeeDTO employeeDTO) {
		employeeServiceManager.save(employeeDTO);
	}
}
