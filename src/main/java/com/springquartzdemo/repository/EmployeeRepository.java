package com.springquartzdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springquartzdemo.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
