package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.cg.entity.DeptManagerS;
import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.TitlesRepository;



@ExtendWith(MockitoExtension.class)
class TitlesServiceImplTest {

	@Mock
	private TitlesRepository titlesRepository;
	
	@Autowired
	@InjectMocks
	TitlesServiceImpl titlesService;
	
	@Test
	void testAssignTitleToEmployee() throws RecordNotFoundException, InvalidDataException {
		TitleProducerS previousRec=new TitleProducerS();
		TitlesPK tpk=new TitlesPK(6, "Senior Staff", LocalDate.of(2013,Month.FEBRUARY, 12));
		previousRec.setTitlesPK(tpk);
		previousRec.setToDate(LocalDate.of(9999,Month.JANUARY, 1));
		
		TitleProducerS newRec=new TitleProducerS();
		TitlesPK tpk1=new TitlesPK(6, "Manager", LocalDate.of(2018,Month.FEBRUARY, 12));
		newRec.setTitlesPK(tpk1);
		newRec.setToDate(LocalDate.of(9999,Month.JANUARY, 1));
		
		int empNo=6;
		
		//stubbing
		when(titlesRepository.findByEmpNoAndMaxFromDate(empNo, empNo)).thenReturn(previousRec);
		when(titlesRepository.save(Mockito.any(TitleProducerS.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		
		String status=titlesService.assignTitleToEmployee(newRec);
		
		assertEquals(status,"Employee is assigned   with title  from fromdate to enddate");
		assertTrue(previousRec.getToDate().equals(newRec.getTitlesPK().getFromDate()));
		
		//verify
		 verify(titlesRepository,times(1)).findByEmpNoAndMaxFromDate(empNo, empNo);		
		 verify(titlesRepository, times(2)).save(Mockito.any(TitleProducerS.class));
		
	}
	
	@Test
	void testAssignTitleToEmployeeDoesNotExists() throws RecordNotFoundException, InvalidDataException {
				
		TitleProducerS newRec=new TitleProducerS();
		TitlesPK tpk1=new TitlesPK(6, "Manager", LocalDate.of(2018,Month.FEBRUARY, 12));
		newRec.setTitlesPK(tpk1);
		newRec.setToDate(LocalDate.of(9999,Month.JANUARY, 1));
		
		int empNo=6;
		
		//stubbing
		when(titlesRepository.findByEmpNoAndMaxFromDate(empNo, empNo)).thenReturn(null);

		
		assertThrows(RecordNotFoundException.class,()-> titlesService.assignTitleToEmployee(newRec));
	
		//verify
		 verify(titlesRepository,times(1)).findByEmpNoAndMaxFromDate(empNo, empNo);		
	
	}
	
	@Test
	void testAssignTitleToEmployeeLessThanFiveYears() throws RecordNotFoundException, InvalidDataException {
		TitleProducerS previousRec=new TitleProducerS();
		TitlesPK tpk=new TitlesPK(6, "Senior Staff", LocalDate.of(2013,Month.FEBRUARY, 12));
		previousRec.setTitlesPK(tpk);
		previousRec.setToDate(LocalDate.of(9999,Month.JANUARY, 1));
		
		TitleProducerS newRec=new TitleProducerS();
		TitlesPK tpk1=new TitlesPK(6, "Manager", LocalDate.of(2016,Month.FEBRUARY, 12));
		newRec.setTitlesPK(tpk1);
		newRec.setToDate(LocalDate.of(9999,Month.JANUARY, 1));
		
		int empNo=6;
		
		//stubbing
		when(titlesRepository.findByEmpNoAndMaxFromDate(empNo, empNo)).thenReturn(previousRec);
//		when(titlesRepository.save(Mockito.any(TitleProducerS.class)))
//        .thenAnswer(i -> i.getArguments()[0]);
		
		assertThrows(InvalidDataException.class,()-> titlesService.assignTitleToEmployee(newRec));
		
	
		//verify
		 verify(titlesRepository,times(1)).findByEmpNoAndMaxFromDate(empNo, empNo);		
		// verify(titlesRepository, times(1)).save(Mockito.any(TitleProducerS.class));
		
	}

	// distinct titles
	@Test
	void testFindDistinctIdByTitle() throws RecordNotFoundException {
		List<String> titlesList=Arrays.asList("Staff","Senior Staff","Manager","Engineer","Assistant Engineer","Senior Engineer");
		
		//stubbing
		when(titlesRepository.findDistinctIdByTitle()).thenReturn(titlesList);
		
		List<String> titles=titlesService.findDistinctIdByTitle();
		
		assertEquals(titles.size(),titlesList.size());
		
		//verify
		verify(titlesRepository,times(1)).findDistinctIdByTitle();
	}
	
	@Test
	void testFindDistinctIdByTitleNotFound() throws RecordNotFoundException {
		List<String> titlesList=Arrays.asList();
		
		//stubbing
		when(titlesRepository.findDistinctIdByTitle()).thenReturn(titlesList);
		
		assertThrows(RecordNotFoundException.class,()-> titlesService.findDistinctIdByTitle());

		//verify
		verify(titlesRepository,times(1)).findDistinctIdByTitle();
	}

}
