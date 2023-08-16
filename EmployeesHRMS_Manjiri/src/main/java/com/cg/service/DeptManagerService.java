package com.cg.service;

import java.util.List;

import com.cg.entity.DeptManagerPK;
import com.cg.entity.DeptManagerS;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;

public interface DeptManagerService {
	public List<DeptManagerS> findByDeptNo(String deptNo) throws RecordNotFoundException;
	public DeptManagerS findByDeptManagerPK(DeptManagerPK deptManagerPK);
	public DeptManagerS findByDeptNoAndMaxFromDate(String deptNo) throws RecordNotFoundException;
	public String assignManagerToDept(DeptManagerS deptmgr) throws RecordNotFoundException, InvalidDataException;
	
}
