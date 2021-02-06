package com.atr.restfull.springbootjpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
			
			//Predicate finalPredicate = cb.or(predicate, predicate);
			// return query.where(cb.and(predicates.toArray(new Predicate[0])))
           //.distinct(true).orderBy(cb.desc(root.get("name")).getRestriction();
			//criteriaQuery.where(finalPredicate);
			//List<Item> items = entityManager.createQuery(criteriaQuery).getResultList();
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

	
	 public Long getSpecificationCount(String id, String firstName, String lastName) {
		 
		return employeeRepository.count((root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();// Returns:and predicate
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
	 public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		    List<Predicate> predicates = new ArrayList<>();

		    if(filter.getName() != null) {
		        predicates.add(cb.equal(root.get("name"), filter.getName());
		    }
		    if(filter.getSurname() != null) {
		        predicates.add(cb.equal(root.get("surname"), filter.getSurname());
		    }
		    if(filter.getAge() != null) {
		        predicates.add(cb.equal(root.get("age"), filter.getAge());
		    }
		    if(predicates.isEmpty()){
		        predicates.add(cb.equal(root.get("id"), -1);
		        /* 
		         I like to add this because without it if no criteria is specified then 
		         everything is returned. Because that's how queries work without where 
		         clauses. However, if my user doesn't provide any criteria I want to 
		         say no results found. 
		        */
		    }

		    return query.where(cb.and(predicates.toArray(new Predicate[0])))
		                .distinct(true).orderBy(cb.desc(root.get("name")).getRestriction();
		}
}
