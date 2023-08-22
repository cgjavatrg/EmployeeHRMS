package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
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
import com.cg.entity.EmployeeDTO;
import com.cg.entity.ManagerDetailsDTO;
import com.cg.exception.RecordNotFoundException;

import java.util.List;
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
	
	

}
