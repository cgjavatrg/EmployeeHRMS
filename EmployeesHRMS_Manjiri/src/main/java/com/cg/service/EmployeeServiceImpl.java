package com.cg.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeDeptDTO;
import com.cg.entity.EmployeeProducerS;
import com.cg.entity.ManagerDetailsDTO;
import com.cg.entity.Role;
import com.cg.entity.Users;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.EmployeeRepository;
import com.cg.repository.UsersRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDTO> findEmployeesByHireDateBetween(int years) throws RecordNotFoundException {
		LocalDate today=LocalDate.now();
		List<EmployeeDTO> elist=employeeRepository.findByHireDateBetween(today.minusYears(years),today );
		if (elist==null || elist.isEmpty()) {
			throw new RecordNotFoundException("No Employees have joined in Last "+years+" years");
		}
		return elist;
	}

	@Override
	public Integer findEmployeesCountByHireDateBetween(int years) {
		// TODO Auto-generated method stub
		LocalDate today=LocalDate.now();
		List<EmployeeDTO> elist=employeeRepository.findByHireDateBetween(today.minusYears(years),today );
		if (elist==null || elist.isEmpty()) {
			return 0;
		}
		return elist.size();

	}

	@Override
	public List<EmployeeDTO> findEmployeesByHireDateYearGreaterThan(int year) throws RecordNotFoundException {
	//	LocalDate startDate=LocalDate.of(year, Month.JANUARY, 1);
		LocalDate endDate=LocalDate.of(year, Month.DECEMBER, 31);
		List<EmployeeDTO> elist=employeeRepository.findByHireDateGreaterThan(endDate);
		if (elist==null || elist.isEmpty()) {
			throw new RecordNotFoundException("No Employees have joined in a year "+year+" !!");
		}
		return elist;
	}

	@Override
	public Integer findEmployeesCountByHireDateYearGreaterthan(int year) {
		LocalDate endDate=LocalDate.of(year, Month.DECEMBER, 31);
		List<EmployeeDTO> elist=employeeRepository.findByHireDateGreaterThan(endDate);
		if (elist==null || elist.isEmpty()) {
			return 0;
		}
		return elist.size();

	}

	@Override
	public List<ManagerDetailsDTO> findManagerDetails() throws RecordNotFoundException {
		List<ManagerDetailsDTO> mgrList = employeeRepository.findManagerDetails();
		if(mgrList==null || mgrList.isEmpty()) {
			throw new RecordNotFoundException("There are no managers !!");
		}
		return mgrList;
	}

	@Override
	public List<EmployeeDeptDTO> findByDeptAndFromDate(String deptNo, LocalDate fromDate)
			throws RecordNotFoundException {
		int year=fromDate.getYear();
		LocalDate startDate=LocalDate.of(year, Month.JANUARY, 1);
		LocalDate endDate= LocalDate.of(year, Month.DECEMBER, 31);
		List<EmployeeDeptDTO> empdeptlist = employeeRepository.findByDeptAndFromDate(deptNo, startDate, endDate);
		if(empdeptlist==null || empdeptlist.isEmpty()) {
			throw new RecordNotFoundException("There are no Employees in department = "+deptNo+" in a year = "+year+" !!");
		}
		
		return empdeptlist;
	}
	@Override
	public EmployeeProducerS signup(EmployeeProducerS emp) {
			
			return employeeRepository.save(emp);
	}
	
	@Override
	public String deleteEmployee(int empno) throws RecordNotFoundException {
		Optional<EmployeeProducerS> opch=employeeRepository.findById(empno);
		if(opch.isEmpty()) {
			throw new RecordNotFoundException("There is no record for empno = "+empno);
		}
		
		employeeRepository.deleteById(empno);
		Optional<EmployeeProducerS> opemp=employeeRepository.findById(empno);
		if(opemp.isEmpty()) {
			return "Employee is deleted successfully";
		}
		else {
			return null;
		}
		
	}
	
	@Override
	public List<EmployeeDTO> findByFormDateGreaterThan(LocalDate fromDate) throws RecordNotFoundException {
		List<EmployeeDTO> elist=employeeRepository.findByFormDateGreaterThan(fromDate);
		if(elist==null || elist.isEmpty()) {
			throw new RecordNotFoundException("No managers after date "+fromDate);
		}
		return elist;
	}

	@Autowired
	UsersRepository usersRepository;
	
		
	@Override
	public Users createUserWithRole(Users user) {
			
			return usersRepository.save(user);
	}

	

	@Override
	public Users findUserByname(String username) {
			int userid=Integer.parseInt(username);
			return usersRepository.findById(userid).get();
	}

	


	
	

}
