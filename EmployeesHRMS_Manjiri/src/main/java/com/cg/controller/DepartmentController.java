package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.DepartmentProducerS;
import com.cg.exception.RecordNotFoundException;
import com.cg.service.DepartmentService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "*")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/departments")
	public List<DepartmentProducerS> getAllDepartments() throws RecordNotFoundException {
		return departmentService.getAllDepartments();
	}
}
