package com.cg.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cg.entity.DeptEmpPK;
import com.cg.entity.DeptEmpProducerS;
import com.cg.entity.EmployeeDTO;
import com.cg.exception.RecordNotFoundException;
import com.cg.security.JwtRequestFilter;
import com.cg.security.JwtUtil;
import com.cg.security.MyUserDetailsService;
import com.cg.service.CurrentDeptEmpService;
import com.cg.service.DeptEmpService;
import com.cg.service.DeptManagerService;
import com.cg.service.EmployeeService;
import com.cg.service.EmployeeServiceImpl;
import com.cg.service.TitlesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(AdminConsumer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // executes test cases in specified order
class AdminConsumerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	@InjectMocks
	private AdminConsumer adminconsumer;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@MockBean
	private DeptEmpService deptEmpService;
	
	@MockBean
	private DeptManagerService deptManagerService;
	
	@MockBean
	private CurrentDeptEmpService currentDeptEmpService;
	
	@MockBean
	private TitlesService titlesService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private  String token;
	
	@BeforeEach
	public void setup() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ADMIN");
		//List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("EMPLOYEE");
		String username="4";
		String password=encoder.encode("4");
		Mockito.when(myUserDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(new User(username, password, authorities));
		token = jwtUtil.generateToken(new User(username, password, authorities));
	}
	
	// testing list employees joined last some years
	
	@Test
	@Order(1)
	void testgetEmployeesByHireDateIn() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(2,"Sham","Kumar",LocalDate.of(2019, Month.APRIL, 23)));
		elist.add(new EmployeeDTO(3,"Krishna","Sharma",LocalDate.of(2023, Month.DECEMBER, 5)));
		int years=10;
		when(employeeService.findEmployeesByHireDateBetween(years)).thenReturn(elist);
		
		String url="/api/v1/adminhrmsconsumer/employees/"+years;
		
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
	void testgetEmployeesByHireDateInInvalidYears() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		int years=0;
		when(employeeService.findEmployeesByHireDateBetween(years)).thenReturn(elist);
		
		String url="/api/v1/adminhrmsconsumer/employees/"+years;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
		
	}
	
	// testing count of employees joined last some years
	
	@Test
	@Order(3)
	public void testfindEmployeesCountByHireDateBetween() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(2,"Sham","Kumar",LocalDate.of(2019, Month.APRIL, 23)));
		elist.add(new EmployeeDTO(3,"Krishna","Sharma",LocalDate.of(2023, Month.DECEMBER, 5)));
		int years=10;
		
		// stubbing
		when(employeeService.findEmployeesCountByHireDateBetween(years)).thenReturn(elist.size());
		
		String url="/api/v1/adminhrmsconsumer/employees/count/"+years;
		
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
	public void testfindEmployeesCountZeroByHireDateBetween() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		int years=0;
		
		// stubbing
		when(employeeService.findEmployeesCountByHireDateBetween(years)).thenReturn(elist.size());
		
		String url="/api/v1/adminhrmsconsumer/employees/count/"+years;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
		
	}
	
	// list of employees joined after perticular year
	@Test
	@Order(5)
	public void testfindEmployeesByHireDateYear() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(5,"Geetha","Datta",LocalDate.of(2014, Month.JULY, 27)));
		int year=2013;
		
		when(employeeService.findEmployeesByHireDateYearGreaterThan(year)).thenReturn(elist);
		
		String url="/api/v1/adminhrmsconsumer/employees/year/"+year;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.size()").value(elist.size()))
				;
		
	}
	
	@Test
	@Order(6)
	public void testfindEmployeesByHireDateYearEmptyList() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		int year=1983;
		
		when(employeeService.findEmployeesByHireDateYearGreaterThan(year)).thenReturn(elist);
		
		String url="/api/v1/adminhrmsconsumer/employees/year/"+year;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
		
	}
	
	//count of employees joined after perticular year
	@Test
	@Order(7)
	public void testfindEmployeesCountByHireDateYear() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		elist.add(new EmployeeDTO(1,"Ram","Vyas",LocalDate.of(2014, Month.AUGUST, 12)));
		elist.add(new EmployeeDTO(5,"Geetha","Datta",LocalDate.of(2014, Month.JULY, 27)));
		int year=2013;
		
		when(employeeService.findEmployeesCountByHireDateYearGreaterthan(year)).thenReturn(elist.size());
		
		String url="/api/v1/adminhrmsconsumer/employees/year/count/"+year;
		
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
	public void testfindEmployeesCountZeroByHireDateYear() throws Exception {
		List<EmployeeDTO> elist=new ArrayList<>();
		int year=1983;
		
		when(employeeService.findEmployeesCountByHireDateYearGreaterthan(year)).thenReturn(elist.size());
		
		String url="/api/v1/adminhrmsconsumer/employees/year/count/"+year;
		
		mockMvc.perform(
				MockMvcRequestBuilders.get(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				;
	}
	
	// test delete Employee by empNo
	@Test
	@Order(9)
	public void testdeleteEmployee() throws Exception {
		int empNo=1;
		String url="/api/v1/adminhrmsconsumer/employee/"+empNo;
		
		when(employeeService.deleteEmployee(empNo)).thenReturn("Employee is deleted successfully");
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				;
	}
	
	@Test
	@Order(10)
	public void testdeleteEmployeeDoesNotExist() throws Exception {
		int empNo=10;
		String url="/api/v1/adminhrmsconsumer/employee/"+empNo;
		
		when(employeeService.deleteEmployee(empNo)).thenThrow(RecordNotFoundException.class);
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete(url)
				.header("AUTHORIZATION","Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				;
	}
	
	
	
	//Testing assign department to Employee
	@Autowired
	  private ObjectMapper objectMapper;

	
	@Test
	@Order(11)
	public void testassignDeptToEmp() throws JsonProcessingException, Exception  {
		DeptEmpProducerS samerecord=new DeptEmpProducerS();
		samerecord.setDeptEmpPK(new DeptEmpPK(6,"d007"));
		samerecord.setFromDate(LocalDate.of(2014,Month.AUGUST, 16));
		samerecord.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		when(deptEmpService.findByDeptEmpPK(samerecord.getDeptEmpPK())).thenReturn(samerecord);
		
		DeptEmpProducerS newRec=new DeptEmpProducerS();
		newRec.setDeptEmpPK(new DeptEmpPK(6,"d007"));
		newRec.setFromDate(LocalDate.of(2019,Month.AUGUST, 16));
		newRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		String url="/api/v1/adminhrmsconsumer/assigndept";
		
		 mockMvc.perform(post(url)
				 .contentType(MediaType.APPLICATION_JSON)
				 .header("AUTHORIZATION","Bearer "+token)
			        .content(objectMapper.writeValueAsString(newRec)))
			        .andExpect(status().isBadRequest())
			        .andDo(print());
		
		
	}
	
	
	@Test
	@Order(12)
	public void testassignDeptToEmp1() throws JsonProcessingException, Exception  {
		DeptEmpProducerS previousDeptEmp=new DeptEmpProducerS();
		previousDeptEmp.setDeptEmpPK(new DeptEmpPK(6,"d007"));
		previousDeptEmp.setFromDate(LocalDate.of(2014,Month.AUGUST, 16));
		previousDeptEmp.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		when(deptEmpService.findByDeptEmpPK(previousDeptEmp.getDeptEmpPK())).thenReturn(null);
		when(deptEmpService.findByEmpNoAndMaxFromDate(6)).thenReturn(previousDeptEmp);
		
		// from date of new record is same as previous record
		DeptEmpProducerS newRec=new DeptEmpProducerS();
		newRec.setDeptEmpPK(new DeptEmpPK(6,"d009"));
		newRec.setFromDate(LocalDate.of(2014,Month.AUGUST, 16));
		newRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		String url="/api/v1/adminhrmsconsumer/assigndept";
		
		 mockMvc.perform(post(url)
				 .contentType(MediaType.APPLICATION_JSON)
				 .header("AUTHORIZATION","Bearer "+token)
			        .content(objectMapper.writeValueAsString(newRec)))
			        .andExpect(status().isBadRequest())
			        .andDo(print());
		
		
	}
	
	@Test
	@Order(13)
	public void testassignDeptToEmp2() throws JsonProcessingException, Exception  {
		
		DeptEmpProducerS previousDeptEmp=new DeptEmpProducerS();
		previousDeptEmp.setDeptEmpPK(new DeptEmpPK(6,"d007"));
		previousDeptEmp.setFromDate(LocalDate.of(2014,Month.AUGUST, 16));
		previousDeptEmp.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		when(deptEmpService.findByDeptEmpPK(previousDeptEmp.getDeptEmpPK())).thenReturn(null);
		when(deptEmpService.findByEmpNoAndMaxFromDate(6)).thenReturn(previousDeptEmp);
		
		//frodate is after todate
		DeptEmpProducerS newRec=new DeptEmpProducerS();
		newRec.setDeptEmpPK(new DeptEmpPK(6,"d009"));
		newRec.setFromDate(LocalDate.of(2019,Month.AUGUST, 16));
		newRec.setToDate(LocalDate.of(2018, Month.JANUARY, 1));
		
		String url="/api/v1/adminhrmsconsumer/assigndept";
		
		 mockMvc.perform(post(url)
				 .contentType(MediaType.APPLICATION_JSON)
				 .header("AUTHORIZATION","Bearer "+token)
			        .content(objectMapper.writeValueAsString(newRec)))
			        .andExpect(status().isBadRequest())
			        .andDo(print());
		
		
	}
	
	@Test
	@Order(14)
	public void testassignDeptToEmpSuccess() throws JsonProcessingException, Exception  {
		
		DeptEmpProducerS previousDeptEmp=new DeptEmpProducerS();
		previousDeptEmp.setDeptEmpPK(new DeptEmpPK(6,"d007"));
		previousDeptEmp.setFromDate(LocalDate.of(2014,Month.AUGUST, 16));
		previousDeptEmp.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		when(deptEmpService.findByDeptEmpPK(previousDeptEmp.getDeptEmpPK())).thenReturn(null);
		when(deptEmpService.findByEmpNoAndMaxFromDate(6)).thenReturn(previousDeptEmp);
		
		//frodate is after todate
		DeptEmpProducerS newRec=new DeptEmpProducerS();
		newRec.setDeptEmpPK(new DeptEmpPK(6,"d009"));
		newRec.setFromDate(LocalDate.of(2019,Month.AUGUST, 16));
		newRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		when(deptEmpService.assignDeptToEmp(newRec)).thenReturn("Employee is assigned to the department successfully.");
		
		String url="/api/v1/adminhrmsconsumer/assigndept";
		
		 mockMvc.perform(post(url)
				 .contentType(MediaType.APPLICATION_JSON)
				 .header("AUTHORIZATION","Bearer "+token)
			        .content(objectMapper.writeValueAsString(newRec)))
			        .andExpect(status().isOk())
			        .andDo(print());
		
		
	}
	



}
