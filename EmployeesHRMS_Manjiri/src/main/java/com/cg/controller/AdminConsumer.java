package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.CurrentDeptEmp;
import com.cg.entity.DeptEmpProducerS;
import com.cg.entity.DeptManagerS;
import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeProducerS;
import com.cg.entity.Role;
import com.cg.entity.TitleProducerS;
import com.cg.entity.Users;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;
import com.cg.service.CurrentDeptEmpService;
import com.cg.service.DeptEmpService;
import com.cg.service.DeptManagerService;
import com.cg.service.EmployeeService;
import com.cg.service.TitlesService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "*")
public class AdminConsumer {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PasswordEncoder encoder;
	
	// for user signup
	
	@PostMapping("/adminhrmsconsumer/user/role")
	public Users createUserWithRole(@RequestBody Users user) {
		user.setRole(Role.ADMIN);
		user.setPassword(encoder.encode(user.getPassword()));
		return employeeService.createUserWithRole(user);
	}
	
	@PostMapping("/adminhrmsconsumer/user/signup")
	public EmployeeProducerS signup(@RequestBody EmployeeProducerS emp) {
		emp.getUsers().setRole(Role.ADMIN);
		emp.getUsers().setPassword(encoder.encode(emp.getUsers().getPassword()));
		return employeeService.signup(emp);
	}
	
	//for Employee services
	
	@GetMapping("/adminhrmsconsumer/employees/{noofyear}")
	public List<EmployeeDTO> getEmployeesByHireDateIn(@PathVariable("noofyear")int yesrs) throws RecordNotFoundException, InvalidDataException {
		if(yesrs<=0) {
			throw new InvalidDataException("Invalid Number of years. Minimum years should be 1");
		}
		return employeeService.findEmployeesByHireDateBetween(yesrs);
	}
	
	@GetMapping("/adminhrmsconsumer/employees/count/{noofyear}")
	public Integer findEmployeesCountByHireDateBetween(@PathVariable("noofyear")int years) throws InvalidDataException {
		if(years<=0) {
			throw new InvalidDataException("Invalid Number of years. Minimum years should be 1");
		}
		return employeeService.findEmployeesCountByHireDateBetween(years);
	}

	@GetMapping("/adminhrmsconsumer/employees/year/{year}")
	List<EmployeeDTO> findEmployeesByHireDateYear(@PathVariable("year")int year) throws RecordNotFoundException, InvalidDataException {
		if(year<1984) {
			throw new InvalidDataException("Invalid Year. Year should start from 1984.");
		}
		return employeeService.findEmployeesByHireDateYearGreaterThan(year);
	}
	
	@GetMapping("/adminhrmsconsumer/employees/year/count/{year}")
	public Integer findEmployeesCountByHireDateYear(@PathVariable("year")int year) throws InvalidDataException {
		if(year<1984) {
			throw new InvalidDataException("Invalid Year. Year should start from 1984.");
		}
		return employeeService.findEmployeesCountByHireDateYearGreaterthan(year);
	}
	
	@DeleteMapping("/adminhrmsconsumer/employee/{empno}")
	public String deleteEmployee(@PathVariable("empno") int empno) throws RecordNotFoundException {
		return employeeService.deleteEmployee(empno);
	}
	
	//for department employee services
	
	@Autowired
	DeptEmpService deptEmpService;
	
	@GetMapping("/deptemp/empno/{empno}")
	public List<DeptEmpProducerS> findByEmpNo(@PathVariable("empno")int  empNo) throws RecordNotFoundException, InvalidDataException {
		if(empNo < 1) {
			throw new InvalidDataException("Invalid empNo. 'empNo' must be greater than or equal to 1");
		}
		return deptEmpService.findByEmpNo(empNo);
	}
	
//	@GetMapping("/deptemp/empnoAndMaxFromDate/{empno}")
//	public DeptEmpProducerS findByEmpNoAndMaxFromDate(int empNo) throws RecordNotFoundException, InvalidDataException {
//		if(empNo <=10000) {
//			throw new InvalidDataException("Invalid empNo. 'empNo' must be greater than 10000");
//		}
//		return deptEmpService.findByEmpNoAndMaxFromDate(empNo);
//	}
	
	@PostMapping("/adminhrmsconsumer/assigndept")
	public String assignDeptToEmp(@RequestBody DeptEmpProducerS deptEmp) throws RecordNotFoundException, InvalidDataException {
		DeptEmpProducerS rec=deptEmpService.findByDeptEmpPK(deptEmp.getDeptEmpPK());
		if(rec!=null) {
			throw new InvalidDataException("Record exist. Please assign different Department");
		}
		
		
		DeptEmpProducerS previousDeptEmp=deptEmpService.findByEmpNoAndMaxFromDate(deptEmp.getDeptEmpPK().getEmpNo());
		if(deptEmp.getFromDate().equals(previousDeptEmp.getFromDate()) || deptEmp.getFromDate().isBefore(previousDeptEmp.getFromDate())) {
			throw new InvalidDataException("Invalid From Date");
		}
			
		
		if(deptEmp.getFromDate().equals(deptEmp.getToDate()) || deptEmp.getFromDate().isAfter(deptEmp.getToDate())) {
			throw new InvalidDataException("Invalid Dates. 'fromDate' should be before 'toDate'");
		}
		return deptEmpService.assignDeptToEmp(deptEmp);
	}
	
	// for Dept Manager services
	
	@Autowired
	DeptManagerService deptManagerService;
	
	@Autowired
	CurrentDeptEmpService currentDeptEmpService;
	
	@GetMapping("/deptmanager/deptNo/{deptNo}")
	public List<DeptManagerS> findByDeptNo(@PathVariable("deptNo")String deptNo) throws RecordNotFoundException, InvalidDataException { 
		if(!deptNo.matches("[d]\\d{3}")) {
			throw new InvalidDataException("Invalid Department Number. Department Number should be in format 'd001' ");
		}
		
		return deptManagerService.findByDeptNo(deptNo);
	}
	
	@PostMapping("/adminpayrollconsumer/assignmgr")
	public String assignManagerToDept(@RequestBody DeptManagerS deptmgr) throws RecordNotFoundException, InvalidDataException{
		DeptManagerS rec=deptManagerService.findByDeptManagerPK(deptmgr.getDeptManagerPK());
		if(rec!=null) {
			throw new InvalidDataException("Record exists. Please assign different employee as manager");
		}
		
		CurrentDeptEmp currdeptemp=currentDeptEmpService.findByEmpno(deptmgr.getDeptManagerPK().getEmpNo());

		if(currdeptemp!=null  ) {
			if(!currdeptemp.getDeptNo().equals(deptmgr.getDeptManagerPK().getDeptNo())) {
				throw new InvalidDataException("Employee is not in the same department. Assign Same department employee as Manager");
			}
		}
	
		DeptManagerS previousdeptmgr=deptManagerService.findByDeptNoAndMaxFromDate(deptmgr.getDeptManagerPK().getDeptNo());
		if(deptmgr.getFromDate().equals(previousdeptmgr.getFromDate()) || deptmgr.getFromDate().isBefore(previousdeptmgr.getFromDate())) {
			throw new InvalidDataException("Invlid From Date");
		}
		
		if(deptmgr.getFromDate().equals(deptmgr.getToDate()) || deptmgr.getFromDate().isAfter(deptmgr.getToDate())) {
			throw new InvalidDataException("Invalid Dates. 'fromDate' should be before 'toDate'");
		}
		
		return deptManagerService.assignManagerToDept(deptmgr);
	}
	
	// for Titles Services
	 
	@Autowired
	TitlesService titlesService;
	
	@GetMapping("/titles/{empno}")
	public List<TitleProducerS> findTitlesByEmpNo(@PathVariable("empno") int empNo) throws RecordNotFoundException,InvalidDataException {
		if(empNo<1) {
			throw new InvalidDataException("Employee number empno must be greater than or equal to 1");
		}
		return titlesService.findByEmpNo(empNo);
	}
	
	@GetMapping("/titles/distincttitles")
	public List<String> findDistinctIdByTitle() throws RecordNotFoundException {
		return titlesService.findDistinctIdByTitle();
	}
	
	@PostMapping("/adminhrmslconsumer/assigntitle")
	public String assignTitleToEmployee(@RequestBody TitleProducerS titleob) throws RecordNotFoundException, InvalidDataException { 
		TitleProducerS rec=titlesService.findById(titleob.getTitlesPK());
		if(rec!=null) {
			throw new InvalidDataException("Record Exists. Choose another title and from date");
		}
		
		TitleProducerS previousTitle=titlesService.findByEmpNoAndMaxFromDate(titleob.getTitlesPK().getEmpNo());
		if(titleob.getTitlesPK().getTitle().equals(previousTitle.getTitlesPK().getTitle())) {
			throw new InvalidDataException("Change Title");
		}
		
		if(titleob.getTitlesPK().getFromDate().equals(previousTitle.getTitlesPK().getFromDate()) || titleob.getTitlesPK().getFromDate().isBefore(previousTitle.getTitlesPK().getFromDate())) {
			throw new InvalidDataException("Invlid From Date");
		}
		
		
		if(titleob.getTitlesPK().getFromDate().equals(titleob.getToDate()) || titleob.getTitlesPK().getFromDate().isAfter(titleob.getToDate())) {
			throw new InvalidDataException("Invalid Dates. 'fromDate' should be before 'toDate'");
		}
		return titlesService.assignTitleToEmployee(titleob);
	}
}
