package com.springquartzdemo.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springquartzdemo.dto.EmployeeDTO;
import com.springquartzdemo.entity.Employee;
import com.springquartzdemo.repository.EmployeeRepository;
import com.springquartzdemo.service.EmployeeServiceManager;

@Service
public class EmployeeServiceManagerImpl implements EmployeeServiceManager {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public void save(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

}
