package com.springquartzdemo.service;

import java.util.List;

import com.springquartzdemo.dto.EmployeeDTO;
import com.springquartzdemo.entity.Employee;

public interface EmployeeServiceManager {

	void save(EmployeeDTO employeeDTO);
	
	List<Employee> getAllEmployee();

}
