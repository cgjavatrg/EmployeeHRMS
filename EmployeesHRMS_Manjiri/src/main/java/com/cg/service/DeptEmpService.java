package com.cg.service;

import java.util.List;

import com.cg.entity.DeptEmpPK;
import com.cg.entity.DeptEmpProducerS;
import com.cg.exception.RecordNotFoundException;

public interface DeptEmpService {
	List<DeptEmpProducerS> findByEmpNo(int empNo) throws RecordNotFoundException;
	
	DeptEmpProducerS findByEmpNoAndMaxFromDate(int empNo) throws RecordNotFoundException;
	
	DeptEmpProducerS findByDeptEmpPK(DeptEmpPK deptEmpPK) ;
	
	String assignDeptToEmp(DeptEmpProducerS deptEmp) throws RecordNotFoundException;
}
