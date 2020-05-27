package com.atr.restfull.springbootjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.atr.restfull.springbootjpa.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Query("SELECT e FROM Employee e WHERE e.firstName = ?1 AND e.lastName = ?2 ")
	List<Employee> findByFirstNameOrLastName1(String firstName, String lastName);
	
	@Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName ")
	List<Employee> findByFirstNameOrLastName2(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
}
