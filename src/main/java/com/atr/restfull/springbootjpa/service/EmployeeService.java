package com.atr.restfull.springbootjpa.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atr.restfull.springbootjpa.model.Employee;
import com.atr.restfull.springbootjpa.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public List<Employee> getSpecificationEmployee(String id, String firstName, String lastName, Pageable pageable) {
		
		List<Employee> employees = employeeRepository.findAll((Specification<Employee>) (root, query, criteriaBuilder) -> {

			Predicate predicate = criteriaBuilder.conjunction();
			if (Objects.nonNull(id)) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
			}
			if (!StringUtils.isEmpty(firstName)) {
				predicate = criteriaBuilder.and(predicate, 
						criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
			}
			if (!StringUtils.isEmpty(lastName)) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.like(root.get("lastName"), "%" + lastName.toLowerCase() + "%"));
			}
			query.orderBy(criteriaBuilder.desc(root.get("firstName")), criteriaBuilder.asc(root.get("id")));

			return predicate;
		}, pageable).getContent();
		/*
		 * long total =employees.size(); System.out.println("pageable"+pageable);
		 * System.out.println("employees"+employees);
		 */
		return employees;

	}
	
	  public static Specification<Employee> isFirstName(String id, String firstName, String lastName) {
		     return (root, query, cb) -> {
		         return cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
		     };
		  }
	/**
	 * public List<Employee> getSpecificationEmployee(String id, String firstName, String lastName, Pageable pageable) {

		List<Employee> employees = employeeRepository.findAll(new Specification<Employee>() {

			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate predicate = criteriaBuilder.conjunction();
				if (Objects.nonNull(id)) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
				}
				if (StringUtils.isEmpty(firstName)) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("firstName"), firstName));
				}
				if (StringUtils.isEmpty(lastName)) {
					predicate = criteriaBuilder.and(predicate,
							criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
				}
				query.orderBy(criteriaBuilder.desc(root.get("firstName")), criteriaBuilder.asc(root.get("id")));

				return predicate;
			}
		}, pageable).getContent();
		return employees;

	}
	 */
	
	 public Long getSpecificationCount(String id, String firstName, String lastName) {
		 
		return employeeRepository.count((root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			if (Objects.nonNull(id)) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
			}
			if (!StringUtils.isEmpty(firstName)) {
				predicate = criteriaBuilder.and(predicate, 
						criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
			}
			if (!StringUtils.isEmpty(lastName)) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.like(root.get("lastName"), "%" + lastName.toLowerCase() + "%"));
			}
			return predicate;
		});
			

		}

}
