package com.atr.restfull.springbootjpa.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.atr.restfull.springbootjpa.exception.EmployeeNotFoundException;
import com.atr.restfull.springbootjpa.model.Employee;
import com.atr.restfull.springbootjpa.repository.EmployeeRepository;
import com.atr.restfull.springbootjpa.service.EmployeeService;

@RestController
public class WebController {
	
	private final Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping("/")
	public String WELCOME() {
		logger.info("Create log for testing");
		return "Greeting friends";
	}

	@GetMapping("/employees")
	public List<Employee> GetAllEMp() {
		
		return employeeRepository.findAll();
	}
	
	
	@GetMapping("/e")
	public RedirectView  redirectView () {
		Optional<Employee> employee = employeeRepository.findById(1);
		 		String redirectedUrl =employee.get().getFirstName().trim().toString();
		return new RedirectView("https://www.google.com/?code="+redirectedUrl);
	}
	
	@GetMapping("/employee/{id}")
	public Optional<Employee> getEmployeeByID(@PathVariable int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			return employee;
		}
		
		throw new EmployeeNotFoundException("There is No Employee with Id= "+id);
	}
	
	@PostMapping("/employee")
	public void createEmployee(@RequestBody @Valid Employee em) {
		employeeRepository.save(em);
	}
	
	@DeleteMapping("/employee/{id}")
	public void delteEmployee(@PathVariable int id) {
		employeeRepository.deleteById(id);
	}
	
	@PatchMapping("/employee/{id}")
	public void updateEmployee(@RequestBody Employee employee, @PathVariable(value = "id") int id) {
		employee.setId(id);
		employeeRepository.save(employee);
	}
	
	@GetMapping("/employee")
	public Respond GetEmployeeSpecefication(
			@RequestParam(name="id",required = false ) String id,
			@RequestParam(required = false ) String firstName,
			@RequestParam(required = false ) String lastName,
			Pageable pageable
			) {
		Respond respond = new Respond(
				employeeService.getSpecificationCount(id, firstName, lastName), 
				employeeService.getSpecificationEmployee(id, firstName, lastName, pageable ));
		return respond;
	}

}
class Respond {
	private long total;
	
	private List<Employee> employees;

	public Respond(long total, List<Employee> employees) {
		super();
		this.total = total;
		this.employees = employees;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	
	
	
}
