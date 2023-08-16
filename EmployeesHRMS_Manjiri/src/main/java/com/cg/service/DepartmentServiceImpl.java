package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.DepartmentProducerS;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.DepartmentRepositoy;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	DepartmentRepositoy departmentRepository;

	@Override
	public List<DepartmentProducerS> getAllDepartments() throws RecordNotFoundException {
		List<DepartmentProducerS> deptList =departmentRepository.findAll();
		if(deptList==null || deptList.isEmpty()) {
			throw new RecordNotFoundException("There are no departments");
		}
		return deptList;
	}

}
