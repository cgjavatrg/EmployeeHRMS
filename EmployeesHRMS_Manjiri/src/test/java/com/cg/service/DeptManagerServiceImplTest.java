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

import com.cg.repository.DeptManagerRepository;
import com.cg.entity.DeptManagerS;
import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;
import com.cg.entity.DeptEmpProducerS;
import com.cg.entity.DeptManagerPK;


@ExtendWith(MockitoExtension.class)
class DeptManagerServiceImplTest {
	
	@Mock
	private DeptManagerRepository deptManagerRepository;
	
	@Mock
	private TitlesService titlesService;
	
	@Autowired
	@InjectMocks
	private DeptManagerServiceImpl deptManagerService;

	@Test
	public void testassignManagerToDept() throws RecordNotFoundException, InvalidDataException {
		DeptManagerS previousRec = new DeptManagerS();
		previousRec.setDeptManagerPK(new DeptManagerPK(110,"d007"));
		previousRec.setFromDate(LocalDate.of(2013,Month.FEBRUARY, 12));
		previousRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		String deptNo="d007";
		//stubbing
		when(deptManagerRepository.findByDeptNoAndMaxFromDate(deptNo, deptNo)).thenReturn(previousRec);
		
		DeptManagerS deptmgr = new DeptManagerS();
		deptmgr.setDeptManagerPK(new DeptManagerPK(112,"d007"));
		deptmgr.setFromDate(LocalDate.of(2018,Month.FEBRUARY, 12));
		deptmgr.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		TitleProducerS ob=new TitleProducerS();
		TitlesPK tpk=new TitlesPK(deptmgr.getDeptManagerPK().getEmpNo(), "Manager", deptmgr.getFromDate());
		ob.setTitlesPK(tpk);
		ob.setToDate(deptmgr.getToDate());
		
		//stubbing
		when(titlesService.assignTitleToEmployee(ob)).thenReturn("Employee is assigned   with title  from fromdate to enddate");
		when(deptManagerRepository.save(Mockito.any(DeptManagerS.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		
		String status=deptManagerService.assignManagerToDept(deptmgr);
		
		assertEquals(status,"Employee is assigned  as manager for department successfully.");
		assertTrue(previousRec.getToDate().equals(deptmgr.getFromDate()));
		
		//verify
		 verify(deptManagerRepository,times(1)).findByDeptNoAndMaxFromDate(deptNo, deptNo);		
		 verify(deptManagerRepository, times(2)).save(Mockito.any(DeptManagerS.class));
	}
	
	@Test
	public void testassignManagerToDeptDoesNotExists() throws RecordNotFoundException, InvalidDataException {
				
		String deptNo="d007";
		//stubbing
		when(deptManagerRepository.findByDeptNoAndMaxFromDate(deptNo, deptNo)).thenReturn(null);
		
		DeptManagerS deptmgr = new DeptManagerS();
		deptmgr.setDeptManagerPK(new DeptManagerPK(112,"d007"));
		deptmgr.setFromDate(LocalDate.of(2018,Month.FEBRUARY, 12));
		deptmgr.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		assertThrows(RecordNotFoundException.class,()-> deptManagerService.assignManagerToDept(deptmgr));
		
		//verify
		 verify(deptManagerRepository,times(1)).findByDeptNoAndMaxFromDate(deptNo, deptNo);		
	
	}
	
	@Test
	public void testassignManagerToDeptNotEligible() throws RecordNotFoundException, InvalidDataException {
		DeptManagerS previousRec = new DeptManagerS();
		previousRec.setDeptManagerPK(new DeptManagerPK(110,"d007"));
		previousRec.setFromDate(LocalDate.of(2013,Month.FEBRUARY, 12));
		previousRec.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		String deptNo="d007";
		//stubbing
		when(deptManagerRepository.findByDeptNoAndMaxFromDate(deptNo, deptNo)).thenReturn(previousRec);
		
		DeptManagerS deptmgr = new DeptManagerS();
		deptmgr.setDeptManagerPK(new DeptManagerPK(112,"d007"));
		deptmgr.setFromDate(LocalDate.of(2016,Month.FEBRUARY, 12));
		deptmgr.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
		
		TitleProducerS ob=new TitleProducerS();
		TitlesPK tpk=new TitlesPK(deptmgr.getDeptManagerPK().getEmpNo(), "Manager", deptmgr.getFromDate());
		ob.setTitlesPK(tpk);
		ob.setToDate(deptmgr.getToDate());
		// Employee last title assigned from date should be 5 years before new manager from date
		//stubbing
		when(titlesService.assignTitleToEmployee(ob)).thenThrow(new InvalidDataException("Not eligible for promotion. Can not change title"));
		when(deptManagerRepository.save(Mockito.any(DeptManagerS.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		
		assertThrows(InvalidDataException.class,()->deptManagerService.assignManagerToDept(deptmgr));
		
	
		//verify
		 verify(deptManagerRepository,times(1)).findByDeptNoAndMaxFromDate(deptNo, deptNo);		
		 verify(deptManagerRepository, times(1)).save(Mockito.any(DeptManagerS.class));
	}

}
