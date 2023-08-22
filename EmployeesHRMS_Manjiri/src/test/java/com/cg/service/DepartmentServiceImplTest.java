package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.cg.entity.DepartmentProducerS;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.DepartmentRepositoy;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
	
	@Mock
	DepartmentRepositoy departmentRepository;
	
	@Autowired
	@InjectMocks
	DepartmentServiceImpl departmentService;
	
	List<DepartmentProducerS> dlist;
	
	@BeforeEach
	public void setUp() {
		dlist=new ArrayList<>();
		dlist.add(new DepartmentProducerS("d001","Admin"));
		dlist.add(new DepartmentProducerS("d002", "HR"));
		
	}
	
	@AfterEach
	public void tearDown() {
		dlist=null;
	}

	@Test
	void testGetAllDepartments() throws RecordNotFoundException {
		
		//stubbing
		when(departmentRepository.findAll()).thenReturn(dlist);
		
		List<DepartmentProducerS> mydlist=departmentService.getAllDepartments();
		
		assertTrue(mydlist.size()>0);
		assertEquals(mydlist, dlist);
		
		// verify whether departmentRepository findAll() method executed
		verify(departmentRepository,times(1)).findAll();
		
	}
	
	@Test
	void testGetAllDepartmentsEmptyList() throws RecordNotFoundException {
		List<DepartmentProducerS> emptydlist=new ArrayList<>();
		
		//stubbing
		when(departmentRepository.findAll()).thenReturn(emptydlist);
		
		assertThrows(RecordNotFoundException.class, ()->departmentService.getAllDepartments());
			
		// verify whether departmentRepository findAll() method executed
		verify(departmentRepository,times(1)).findAll();
		
	}

}
