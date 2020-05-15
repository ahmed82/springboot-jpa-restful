package com.atr.restfull.springbootjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atr.restfull.springbootjpa.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	
}
