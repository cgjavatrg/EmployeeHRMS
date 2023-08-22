package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.cg.repository.EmployeeRepository;
import com.cg.repository.UsersRepository;
import com.cg.entity.DeptEmpPK;
import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeDeptDTO;
import com.cg.entity.ManagerDetailsDTO;
import com.cg.entity.Role;
import com.cg.entity.SalarieProducerS;
import com.cg.entity.SalariesPK;
import com.cg.entity.EmployeeProducerS;
import com.cg.entity.DeptEmpProducerS;
import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;
import com.cg.entity.Users;
import com.cg.exception.RecordNotFoundException;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock
	UsersRepository usersRepository;
	
	@Autowired
	@InjectMocks
	EmployeeServiceImpl employeeService;
	
	// for last some yeas
	@Test
	void testfindEmployeesByHireDateBetweenLastYears() throws RecordNotFoundException {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(2,"Sham","Kumar",LocalDate.of(2019, Month.APRIL, 23)));
		elist.add(new EmployeeDTO(3,"Krishna","Sharma",LocalDate.of(2023, Month.DECEMBER, 5)));
		int years=10;
		LocalDate beforeTenYears=LocalDate.now().minusYears(years);
		
		//stubing
		when(employeeRepository.findByHireDateBetween(beforeTenYears, LocalDate.now())).thenReturn(elist);
		
		List<EmployeeDTO> myelist=employeeService.findEmployeesByHireDateBetween(years);
		
		assertEquals(myelist,elist);
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateBetween(beforeTenYears, LocalDate.now());
	}
	
	@Test
	void testfindEmployeesByHireDateBetweenLastYearsEmptyList() throws RecordNotFoundException {
		List<EmployeeDTO> elist=new ArrayList<>();
		int years=1;
		LocalDate beforeOneYears=LocalDate.now().minusYears(years);
		
		//stubing
		when(employeeRepository.findByHireDateBetween(beforeOneYears, LocalDate.now())).thenReturn(elist);
		
		assertThrows(RecordNotFoundException.class,()-> employeeService.findEmployeesByHireDateBetween(years));
		
		
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateBetween(beforeOneYears, LocalDate.now());
	}
	
	//Count for last some years
	
	@Test
	public void testfindEmployeesCountByHireDateBetween() {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(2,"Sham","Kumar",LocalDate.of(2019, Month.APRIL, 23)));
		elist.add(new EmployeeDTO(3,"Krishna","Sharma",LocalDate.of(2023, Month.DECEMBER, 5)));
		int years=10;
		LocalDate beforeTenYears=LocalDate.now().minusYears(years);
		
		//stubing
		when(employeeRepository.findByHireDateBetween(beforeTenYears, LocalDate.now())).thenReturn(elist);
		
		int size=employeeService.findEmployeesCountByHireDateBetween(years);
		
		assertTrue(size>0);
		assertEquals(size,elist.size());
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateBetween(beforeTenYears, LocalDate.now());		
		
	}
	
	@Test
	public void testfindEmployeesCountZeroByHireDateBetween() {
		List<EmployeeDTO> elist=new ArrayList<>();
		int years=1;
		LocalDate beforeOneYears=LocalDate.now().minusYears(years);
		
		//stubing
		when(employeeRepository.findByHireDateBetween(beforeOneYears, LocalDate.now())).thenReturn(elist);
		
		int size=employeeService.findEmployeesCountByHireDateBetween(years);
		
		assertTrue(size==0);
		assertEquals(size,elist.size());
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateBetween(beforeOneYears, LocalDate.now());		
		
	}
	
	// tests for hiredate greater than perticular year
	
	@Test
	public void testfindEmployeesByHireDateYearGreaterThan() throws RecordNotFoundException {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(5,"Geetha","Datta",LocalDate.of(2014, Month.JULY, 27)));
		int year=2013;
		LocalDate endDate=LocalDate.of(year, Month.DECEMBER, 31);
		
		//stubing
		when(employeeRepository.findByHireDateGreaterThan(endDate)).thenReturn(elist);
		
		List<EmployeeDTO> myelist=employeeService.findEmployeesByHireDateYearGreaterThan(year);
		
		assertEquals(myelist,elist);
		assertTrue(myelist.get(0).getHireDate().getYear() >year);
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateGreaterThan(endDate);
	}
	
	@Test
	public void testfindEmployeesByHireDateYearGreaterThanEmptyList() throws RecordNotFoundException {
		List<EmployeeDTO> elist=new ArrayList<>();
		
		int year=2023;
		LocalDate endDate=LocalDate.of(year, Month.DECEMBER, 31);
		
		//stubing
		when(employeeRepository.findByHireDateGreaterThan(endDate)).thenReturn(elist);
		
		assertThrows(RecordNotFoundException.class,()-> employeeService.findEmployeesByHireDateYearGreaterThan(year));
		
			
		//verify
		verify(employeeRepository,times(1)).findByHireDateGreaterThan(endDate);
	}
	
	// tests for count of hiredate greater than perticular year
	@Test
	public void testfindEmployeesCountByHireDateYearGreaterThan()  {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(5,"Geetha","Datta",LocalDate.of(2014, Month.JULY, 27)));
		int year=2013;
		LocalDate endDate=LocalDate.of(year, Month.DECEMBER, 31);
		
		//stubing
		when(employeeRepository.findByHireDateGreaterThan(endDate)).thenReturn(elist);
		
		int count=employeeService.findEmployeesCountByHireDateYearGreaterthan(year);
		
		assertEquals(count,elist.size());
		assertTrue(count>0);
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateGreaterThan(endDate);
	}
	
	@Test
	public void testfindEmployeesCountZeroByHireDateYearGreaterThan()  {
		List<EmployeeDTO> emptyelist=new ArrayList<>();
		
		int year=2023;
		LocalDate endDate=LocalDate.of(year, Month.DECEMBER, 31);
		
		//stubing
		when(employeeRepository.findByHireDateGreaterThan(endDate)).thenReturn(emptyelist);
		
		int count=employeeService.findEmployeesCountByHireDateYearGreaterthan(year);
		
		assertEquals(count,emptyelist.size());
		assertTrue(count==0);
		
		//verify
		verify(employeeRepository,times(1)).findByHireDateGreaterThan(endDate);
	}
	
	// test for listing managers
	@Test
	public void testfindManagerDetails() throws RecordNotFoundException {
		List<ManagerDetailsDTO> mgrlist = new ArrayList<>();
		mgrlist.add(new ManagerDetailsDTO(1,"Ram","Vyas",LocalDate.of(1985, Month.APRIL, 22),"Sales"));
		mgrlist.add(new ManagerDetailsDTO(13,"Sham","Gupta",LocalDate.of(1982, Month.JANUARY, 2),"HR"));
		mgrlist.add(new ManagerDetailsDTO(23,"Geetha","Rao",LocalDate.of(1979, Month.AUGUST, 11),"Sales"));
		
		//stubbing
		when(employeeRepository.findManagerDetails()).thenReturn(mgrlist);
		
		List<ManagerDetailsDTO> mlist=employeeService.findManagerDetails();
		
		assertEquals(mlist,mgrlist);
		assertTrue(mlist.size()>0);
		
		//verify
		verify(employeeRepository,times(1)).findManagerDetails();
	
	}
	
	@Test
	public void testfindManagerDetailsEmptyList() throws RecordNotFoundException {
		List<ManagerDetailsDTO> mgrlist = new ArrayList<>();
		//stubbing
		when(employeeRepository.findManagerDetails()).thenReturn(mgrlist);
		
		assertThrows(RecordNotFoundException.class,()-> employeeService.findManagerDetails());
		
			//verify
		verify(employeeRepository,times(1)).findManagerDetails();
	
	}
	
	// test for listing employees in specific department in specific year
	@Test
	public void testfindByDeptAndFromDate() throws RecordNotFoundException {
		List<EmployeeDeptDTO> empdeptlist=new ArrayList<>();
		empdeptlist.add(new EmployeeDeptDTO(2,"Sham","Gupta",LocalDate.of(2014, Month.MARCH, 25),LocalDate.of(2019, Month.MARCH, 25),"HR"));
		//empdeptlist.add(new EmployeeDeptDTO(2,"Sham","Gupta",LocalDate.of(2019, Month.MARCH, 25),LocalDate.of(9999, Month.JANUARY, 1),"Sales"));
		empdeptlist.add(new EmployeeDeptDTO(7,"Geetha","Das",LocalDate.of(2014, Month.SEPTEMBER, 5),LocalDate.of(9999, Month.JANUARY, 5),"HR"));
		
		String deptNo="d002";
		LocalDate myFromDate=LocalDate.of(2014,Month.FEBRUARY , 12);
		int year=myFromDate.getYear();
		LocalDate startDate=LocalDate.of(year, Month.JANUARY, 1);
		LocalDate endDate= LocalDate.of(year, Month.DECEMBER, 31);
		
		// stubbing
		when(employeeRepository.findByDeptAndFromDate(deptNo, startDate, endDate)).thenReturn(empdeptlist);
		
		List<EmployeeDeptDTO> myempdeptlist=employeeService.findByDeptAndFromDate(deptNo, myFromDate);
		assertEquals(myempdeptlist,empdeptlist);
		assertTrue(myempdeptlist.get(0).getFromDate().getYear()==year);
		
		//verify
		verify(employeeRepository,times(1)).findByDeptAndFromDate(deptNo, startDate, endDate);
		
	}
	
	@Test
	public void testfindByDeptAndFromDateEmptyList() throws RecordNotFoundException {
		List<EmployeeDeptDTO> empdeptlist=new ArrayList<>();
	
		
		String deptNo="d002";
		LocalDate myFromDate=LocalDate.of(2014,Month.FEBRUARY , 12);
		int year=myFromDate.getYear();
		LocalDate startDate=LocalDate.of(year, Month.JANUARY, 1);
		LocalDate endDate= LocalDate.of(year, Month.DECEMBER, 31);
		
		// stubbing
		when(employeeRepository.findByDeptAndFromDate(deptNo, startDate, endDate)).thenReturn(empdeptlist);
		
		assertThrows(RecordNotFoundException.class,()-> employeeService.findByDeptAndFromDate(deptNo, myFromDate));
		
		//verify
		verify(employeeRepository,times(1)).findByDeptAndFromDate(deptNo, startDate, endDate);
		
	}
	
	// testing creating employee
	@Test
	public void testsignup() {
		EmployeeProducerS emp=new EmployeeProducerS();
		emp.setEmpNo(6);
		emp.setBirthDate(LocalDate.of(1980,Month.APRIL,3));
		emp.setFirstName("Ram");
		emp.setLastName("Sharma");
		emp.setGender('M');
		emp.setHireDate(LocalDate.of(2005, Month.JUNE, 1));
		
		SalarieProducerS salob=new SalarieProducerS(new SalariesPK(6,LocalDate.of(2005, Month.JUNE, 1)),50000,LocalDate.of(9999, Month.JANUARY, 1));
		List<SalarieProducerS> sallist=new ArrayList<>();
		sallist.add(salob);
		emp.setSalariesCollection(sallist);
		
		DeptEmpProducerS empdept=new DeptEmpProducerS(new DeptEmpPK(6,"d002"),LocalDate.of(2005, Month.JUNE,1),LocalDate.of(9999, Month.JANUARY, 1));
		List<DeptEmpProducerS> empdeptlist=new ArrayList<>();
		empdeptlist.add(empdept);
		emp.setDeptEmpCollection(empdeptlist);
		
		emp.setDeptManagerCollection(null);
		
		TitleProducerS title=new TitleProducerS(new TitlesPK(6,"Staff",LocalDate.of(2005, Month.JUNE,1)),LocalDate.of(9999, Month.JANUARY,1));
		List<TitleProducerS> titlelist=new ArrayList<>();
		titlelist.add(title);
		emp.setTitlesCollection(titlelist);
		
		Users userob=new Users(6,"6",Role.ADMIN);
		emp.setUsers(userob);
		
		// stubbing
		when(employeeRepository.save(emp)).thenReturn(emp);
		
		EmployeeProducerS savedob=employeeService.signup(emp);
		assertEquals(savedob,emp);
		
		verify(employeeRepository,times(1)).save(emp);
	}
	
	//testing deleting employee
	@Test
	public void testdeleteEmployee() throws RecordNotFoundException {
		EmployeeProducerS emp=new EmployeeProducerS();
		emp.setEmpNo(6);
		emp.setBirthDate(LocalDate.of(1980,Month.APRIL,3));
		emp.setFirstName("Ram");
		emp.setLastName("Sharma");
		emp.setGender('M');
		emp.setHireDate(LocalDate.of(2005, Month.JUNE, 1));
		
		SalarieProducerS salob=new SalarieProducerS(new SalariesPK(6,LocalDate.of(2005, Month.JUNE, 1)),50000,LocalDate.of(9999, Month.JANUARY, 1));
		List<SalarieProducerS> sallist=new ArrayList<>();
		sallist.add(salob);
		emp.setSalariesCollection(sallist);
		
		DeptEmpProducerS empdept=new DeptEmpProducerS(new DeptEmpPK(6,"d002"),LocalDate.of(2005, Month.JUNE,1),LocalDate.of(9999, Month.JANUARY, 1));
		List<DeptEmpProducerS> empdeptlist=new ArrayList<>();
		empdeptlist.add(empdept);
		emp.setDeptEmpCollection(empdeptlist);
		
		emp.setDeptManagerCollection(null);
		
		TitleProducerS title=new TitleProducerS(new TitlesPK(6,"Staff",LocalDate.of(2005, Month.JUNE,1)),LocalDate.of(9999, Month.JANUARY,1));
		List<TitleProducerS> titlelist=new ArrayList<>();
		titlelist.add(title);
		emp.setTitlesCollection(titlelist);
		
		Users userob=new Users(6,"6",Role.ADMIN);
		emp.setUsers(userob);
		
		int empno=6;
		
		Optional<EmployeeProducerS> opbeforedelete=Optional.of(emp);
		
		Optional<EmployeeProducerS> opafterDelete=Optional.empty();
		
		//stubbing
		when(employeeRepository.findById(empno)).thenReturn(opbeforedelete).thenReturn(opafterDelete);
		doNothing().when(employeeRepository).deleteById(empno);
		
		String status=employeeService.deleteEmployee(empno);
		assertEquals(status,"Employee is deleted successfully");
		
		//verify
		verify(employeeRepository,times(2)).findById(empno);
		verify(employeeRepository,times(1)).deleteById(empno);
		
	}
	
	@Test
	public void testdeleteEmployeeNotExist() throws RecordNotFoundException {
		
		int empno=7;
		Optional<EmployeeProducerS> opbeforedelete=Optional.empty();
		Optional<EmployeeProducerS> opafterDelete=Optional.empty();
		
		//stubbing
		when(employeeRepository.findById(empno)).thenReturn(opbeforedelete).thenReturn(opafterDelete);
	//	doNothing().when(employeeRepository).deleteById(empno);
		
		assertThrows(RecordNotFoundException.class,()-> employeeService.deleteEmployee(empno));
		
		
		//verify
		verify(employeeRepository,atLeast(1)).findById(empno);
		//verify(employeeRepository,times(1)).deleteById(empno);
		
	}
	
	//test for list of managers who have become manager after specific from date.
	@Test
	public void testfindEmployeesByFromDateGreaterThan() throws RecordNotFoundException {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(5,"Geetha","Datta",LocalDate.of(2016, Month.JULY, 27)));
		
		LocalDate fromDate=LocalDate.of(2014, Month.MARCH, 31);
		
		//stubing
		when(employeeRepository.findByFormDateGreaterThan(fromDate)).thenReturn(elist);
		
		List<EmployeeDTO> myelist=employeeService.findByFormDateGreaterThan(fromDate);
		
		assertEquals(myelist,elist);
			
		//verify
		verify(employeeRepository,times(1)).findByFormDateGreaterThan(fromDate);
	}
	
	@Test
	public void testfindEmployeesEmptyListByFromDateGreaterThan() throws RecordNotFoundException {
		List<EmployeeDTO> elist=new ArrayList<>();
		
		LocalDate fromDate=LocalDate.of(2023, Month.MARCH, 31);
		
		//stubing
		when(employeeRepository.findByFormDateGreaterThan(fromDate)).thenReturn(elist);
		
		assertThrows(RecordNotFoundException.class,()-> employeeService.findByFormDateGreaterThan(fromDate));
		
		//verify
		verify(employeeRepository,times(1)).findByFormDateGreaterThan(fromDate);
	}
	
}
