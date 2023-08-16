package com.cg.service;

import java.util.List;

import com.cg.exception.RecordNotFoundException;
import com.cg.entity.DepartmentProducerS;

public interface DepartmentService {
	List<DepartmentProducerS> getAllDepartments() throws RecordNotFoundException;

}
