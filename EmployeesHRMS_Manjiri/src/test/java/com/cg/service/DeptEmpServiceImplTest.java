package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.cg.entity.DeptEmpPK;
import com.cg.entity.DeptEmpProducerS;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.DeptEmpRepository;

@ExtendWith(MockitoExtension.class)
class DeptEmpServiceImplTest {
	
	@Mock
	DeptEmpRepository deptEmpRepository;
	
	@Autowired
	@InjectMocks
	DeptEmpServiceImpl deptEmpService;

	@Test
	public void testassignDeptToEmp() throws RecordNotFoundException {
		DeptEmpProducerS previousEmpDept=new DeptEmpProducerS();
		previousEmpDept.setDeptEmpPK(new DeptEmpPK(6,"d007"));
		previousEmpDept.setFromDate(LocalDate.of(2014,Month.AUGUST, 16));
		previousEmpDept.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		int empNo=6;
		// stubbing
		when(deptEmpRepository.findByEmpNoAndMaxFromDate(empNo,empNo)).thenReturn(previousEmpDept);
		when(deptEmpRepository.save(Mockito.any(DeptEmpProducerS.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		
		DeptEmpProducerS newRec=new DeptEmpProducerS();
		newRec.setDeptEmpPK(new DeptEmpPK(6,"d008"));
		newRec.setFromDate(LocalDate.of(2019, Month.AUGUST, 16));
		newRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		String status=deptEmpService.assignDeptToEmp(newRec);
		
		assertEquals(status,"Employee is assigned to the department successfully.");
		assertTrue(previousEmpDept.getToDate().equals(newRec.getFromDate()));
		
		//verify
		 verify(deptEmpRepository,times(1)).findByEmpNoAndMaxFromDate(empNo,empNo);		
		 verify(deptEmpRepository, times(2)).save(Mockito.any(DeptEmpProducerS.class));
	}
	
	@Test
	public void testassignDeptToEmpNoEmp() throws RecordNotFoundException {
	
		int empNo=7;
		// stubbing
		when(deptEmpRepository.findByEmpNoAndMaxFromDate(empNo,empNo)).thenReturn(null);
//		when(deptEmpRepository.save(Mockito.any(DeptEmpProducerS.class)))
//        .thenAnswer(i -> i.getArguments()[0]);
		
		DeptEmpProducerS newRec=new DeptEmpProducerS();
		newRec.setDeptEmpPK(new DeptEmpPK(7,"d008"));
		newRec.setFromDate(LocalDate.of(2019, Month.AUGUST, 16));
		newRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		assertThrows(RecordNotFoundException.class,()-> deptEmpService.assignDeptToEmp(newRec));
		
	
		//verify
		 verify(deptEmpRepository,times(1)).findByEmpNoAndMaxFromDate(empNo,empNo);		
	//	 verify(deptEmpRepository, times(2)).save(Mockito.any(DeptEmpProducerS.class));
	}


}
