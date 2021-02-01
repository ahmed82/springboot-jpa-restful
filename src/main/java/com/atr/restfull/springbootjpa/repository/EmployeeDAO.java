package com.atr.restfull.springbootjpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.atr.restfull.springbootjpa.model.Employee;

@Repository
@Transactional
public interface EmployeeDAO extends JpaRepository<Employee, Integer> {
	List<Employee> findByEmployeeNameNotIn(List<String> names); // Spring JPA In cause using method name

	@Query("SELECT e FROM Employee e WHERE e.employeeName NOT IN (:names)") // Spring JPA In cause using @Query
	List<Employee> findByEmployeeNamesNot(@Param("names") List<String> names);

	@Query(nativeQuery = true, value = "SELECT * FROM Employee as e WHERE e.employeeName NOT IN (:names)") // Spring JPA
																											// In cause
																											// query
	List<Employee> findByEmployeeNameNot(@Param("names") List<String> names);
}