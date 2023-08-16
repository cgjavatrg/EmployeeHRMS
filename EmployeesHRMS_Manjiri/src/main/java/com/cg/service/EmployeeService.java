package com.cg.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeDeptDTO;
import com.cg.entity.EmployeeProducerS;
import com.cg.entity.ManagerDetailsDTO;
import com.cg.entity.Users;
import com.cg.exception.RecordNotFoundException;

public interface EmployeeService {
	List<EmployeeDTO> findEmployeesByHireDateBetween(int years) throws RecordNotFoundException;
	
	Integer findEmployeesCountByHireDateBetween(int years) ;
	
	List<EmployeeDTO> findEmployeesByHireDateYearGreaterThan(int year) throws RecordNotFoundException;
	
	Integer findEmployeesCountByHireDateYearGreaterthan(int year) ;
	
	List<ManagerDetailsDTO> findManagerDetails() throws RecordNotFoundException;
	
	List<EmployeeDeptDTO> findByDeptAndFromDate(String deptNo, LocalDate fromDate) throws RecordNotFoundException;
	
	Users createUserWithRole(Users user);
	
	EmployeeProducerS signup(EmployeeProducerS emp);

	Users findUserByname(String username);
	
	String deleteEmployee(int empno) throws RecordNotFoundException;
	
	List<EmployeeDTO> findByFormDateGreaterThan(LocalDate fromDate) throws RecordNotFoundException;
}
