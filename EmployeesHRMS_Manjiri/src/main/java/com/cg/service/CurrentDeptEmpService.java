package com.cg.service;

import com.cg.entity.CurrentDeptEmp;
import com.cg.exception.RecordNotFoundException;

public interface CurrentDeptEmpService {
	public CurrentDeptEmp findByEmpno(int empNo) throws RecordNotFoundException;
}
