package com.cg.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeDeptDTO;
import com.cg.entity.ManagerDetailsDTO;
import com.cg.exception.RecordNotFoundException;
import com.cg.security.JwtRequestFilter;
import com.cg.security.JwtUtil;
import com.cg.security.MyUserDetailsService;
import com.cg.service.EmployeeService;
import com.cg.service.TitlesService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // executes test cases in specified order
class EmployeeHRMSConsumerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	@InjectMocks
	private EmployeeHRMSConsumer employeeHRMSConsumer;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private TitlesService titlesService;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private  String token;
	
	@BeforeEach
	public void setup() {
		//List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ADMIN");
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("EMPLOYEE");
		String username="6";
		String password=encoder.encode("6");
		Mockito.when(myUserDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(new User(username, password, authorities));
		token = jwtUtil.generateToken(new User(username, password, authorities));
	}

	// testing listing managers
	@Test
	@Order(1)
	void testFindManagerDetails() throws Exception {
		List<ManagerDetailsDTO> mgrlist = new ArrayList<>();
		mgrlist.add(new ManagerDetailsDTO(1,"Ram","Vyas",LocalDate.of(1985, Month.APRIL, 22),"Sales"));
		mgrlist.add(new ManagerDetailsDTO(13,"Sham","Gupta",LocalDate.of(1982, Month.JANUARY, 2),"HR"));
		mgrlist.add(new ManagerDetailsDTO(23,"Geetha","Rao",LocalDate.of(1979, Month.AUGUST, 11),"Sales"));
		
		when(employeeService.findManagerDetails()).thenReturn(mgrlist);
		
		String url="/api/v1/employeehrmsconsumer/manager";
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				;
	}
	
	@Test
	@Order(2)
	void testFindManagerDetailsDoNotExists() throws Exception {
	
		when(employeeService.findManagerDetails()).thenThrow(new RecordNotFoundException("No Manager records"));
		
		String url="/api/v1/employeehrmsconsumer/manager";
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				;
	}

	// testing list of employees in specific department in specific year
	@Test
	@Order(3)
	void testFindByDeptAndFrmDate() throws Exception {
		List<EmployeeDeptDTO> empdeptlist=new ArrayList<>();
		empdeptlist.add(new EmployeeDeptDTO(2,"Sham","Gupta",LocalDate.of(2014, Month.MARCH, 25),LocalDate.of(2019, Month.MARCH, 25),"HR"));
		empdeptlist.add(new EmployeeDeptDTO(7,"Geetha","Das",LocalDate.of(2014, Month.SEPTEMBER, 5),LocalDate.of(9999, Month.JANUARY, 5),"HR"));
			
		String deptNo="d002";
		LocalDate myFromDate=LocalDate.of(2014,Month.FEBRUARY , 12);

		String url="/api/v1/employeehrmsconsumer/employees/department/"+deptNo+"/fromdate/"+myFromDate;
		
		when(employeeService.findByDeptAndFromDate(deptNo, myFromDate)).thenReturn(empdeptlist);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				;
	}
	
	@Test
	@Order(4)
	void testFindByDeptAndFrmDateInvalidDepartment() throws Exception {
		
		String deptNo="d02";
		LocalDate myFromDate=LocalDate.of(2014,Month.FEBRUARY , 12);

		String url="/api/v1/employeehrmsconsumer/employees/department/"+deptNo+"/fromdate/"+myFromDate;
		
				
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
	}
	
	@Test
	@Order(5)
	void testFindByDeptAndFrmDateWrongYear() throws Exception {
			
		String deptNo="d002";
		LocalDate myFromDate=LocalDate.of(1983,Month.FEBRUARY , 12);

		String url="/api/v1/employeehrmsconsumer/employees/department/"+deptNo+"/fromdate/"+myFromDate;
		
			
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
	}
	
	@Test
	@Order(6)
	void testFindByDeptAndFrmDateNotFound() throws Exception {
		
		String deptNo="d002";
		LocalDate myFromDate=LocalDate.of(2014,Month.FEBRUARY , 12);

		String url="/api/v1/employeehrmsconsumer/employees/department/"+deptNo+"/fromdate/"+myFromDate;
		
		when(employeeService.findByDeptAndFromDate(deptNo, myFromDate)).thenThrow(new RecordNotFoundException("No Records"));
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				;
	}

	// testing employee as manager after fromdate
	@Test
	@Order(7)
	void testFindByFormDateGreaterThan() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(5,"Geetha","Datta",LocalDate.of(2016, Month.JULY, 27)));
		
		LocalDate fromDate=LocalDate.of(2014, Month.MARCH, 31);
		
		when(employeeService.findByFormDateGreaterThan(fromDate)).thenReturn(elist);
		
		String url="/api/v1/employeehrmsconsumer/manager/"+fromDate;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				;
	}

	@Test
	@Order(8)
	void testFindByFormDateGreaterThanEmptyList() throws Exception {
		LocalDate fromDate=LocalDate.of(2019, Month.MARCH, 31);
		
		when(employeeService.findByFormDateGreaterThan(fromDate)).thenThrow(new RecordNotFoundException("No Records of Managers"));
		
		String url="/api/v1/employeehrmsconsumer/manager/"+fromDate;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				;
	}

	// testing distinct titles
	@Test
	@Order(9)
	void testFindDistinctIdByTitle() throws Exception {
		List<String> titlesList=Arrays.asList("Staff","Senior Staff","Manager","Engineer","Assistant Engineer","Senior Engineer");

		when(titlesService.findDistinctIdByTitle()).thenReturn(titlesList);
		
		String url="/api/v1/employeehrmsconsumer/designations";
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				;
	}
	
	@Test
	@Order(10)
	void testFindDistinctIdByTitleEmptyList() throws Exception {
		
		when(titlesService.findDistinctIdByTitle()).thenThrow(new RecordNotFoundException("No Title Records "));
		
		String url="/api/v1/employeehrmsconsumer/designations";
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				;
	}

}
