package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.CurrentDeptEmp;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.CurrentDeptEmpRepository;

@Service("currentDeptEmpService")
public class CurrentDeptEmpServiceImpl implements CurrentDeptEmpService{
	
	@Autowired
	CurrentDeptEmpRepository currentDeptEmpRepository;

	@Override
	public CurrentDeptEmp findByEmpno(int empNo) throws RecordNotFoundException {
		return currentDeptEmpRepository.findById(empNo).orElseThrow(()->
				new RecordNotFoundException("No Latest Employee Department Record exists for empNo="+empNo));
	}

}
