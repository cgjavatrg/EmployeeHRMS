package com.cg.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeDeptDTO;
import com.cg.entity.EmployeeProducerS;
import com.cg.entity.ManagerDetailsDTO;
import com.cg.entity.Role;
import com.cg.entity.Users;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;
import com.cg.service.EmployeeService;
import com.cg.service.TitlesService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "*")
public class EmployeeHRMSConsumer {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("/employeehrmsconsumer/user/role")
	public Users createUserWithRole(@RequestBody Users user) {
		user.setRole(Role.EMPLOYEE);
		user.setPassword(encoder.encode(user.getPassword()));
		return employeeService.createUserWithRole(user);
	}
	
	@PostMapping("/employeehrmsconsumer/user/signup")
	public EmployeeProducerS signup(@RequestBody EmployeeProducerS emp) {
		emp.getUsers().setRole(Role.EMPLOYEE);
		emp.getUsers().setPassword(encoder.encode(emp.getUsers().getPassword()));
		return employeeService.signup(emp);
	}

	@GetMapping("/employeehrmsconsumer/manager")
	public List<ManagerDetailsDTO> findManagerDetails() throws RecordNotFoundException {
		return employeeService.findManagerDetails();
	}
	
	@GetMapping("/employeehrmsconsumer/employees/department/{deptno}/fromdate/{fromdate}")
	public List<EmployeeDeptDTO> findByDeptAndFrmDate(@PathVariable("deptno")String deptNo, @PathVariable("fromdate") LocalDate fromDate) throws RecordNotFoundException, InvalidDataException {
		System.out.println("Department is "+deptNo);
		if(!deptNo.matches("[d]\\d{3}")) {
			throw new InvalidDataException("Invalid Department Number. Department Number should be in format 'd001' ");
		}
		
		if(fromDate.getYear() < 1985) {
			throw new InvalidDataException("Invalid Year!! Year should be after 1985");
		}
		
		return employeeService.findByDeptAndFromDate(deptNo, fromDate);
	}
	
	@GetMapping("/employeehrmsconsumer/manager/{fromdate}")
	public List<EmployeeDTO> findByFormDateGreaterThan(@PathVariable("fromdate") LocalDate fromDate) throws RecordNotFoundException {
		return employeeService.findByFormDateGreaterThan(fromDate);
	}
	
	@Autowired
	TitlesService titlesService;
	
	@GetMapping("/employeehrmsconsumer/designations")
	public List<String> findDistinctIdByTitle() throws RecordNotFoundException {
		return titlesService.findDistinctIdByTitle();
	}
}
