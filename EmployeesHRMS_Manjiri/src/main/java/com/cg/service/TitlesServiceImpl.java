package com.cg.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.TitlesRepository;

@Service("titlesService")
public class TitlesServiceImpl implements TitlesService{
	@Autowired
	TitlesRepository titlesRepository;
	
	@Override
	public TitleProducerS findByEmpNoAndMaxFromDate(int empNo) throws RecordNotFoundException {
		TitleProducerS titleob=titlesRepository.findByEmpNoAndMaxFromDate(empNo, empNo);
		if(titleob==null) {
			throw new RecordNotFoundException("No such Record in Titles for empNo = "+empNo);
		}
		return titleob;
	}

	@Override
	public String assignTitleToEmployee(TitleProducerS titleob) throws RecordNotFoundException, InvalidDataException {
		TitleProducerS previousTitleob=this.findByEmpNoAndMaxFromDate(titleob.getTitlesPK().getEmpNo());
		Period p=Period.between(previousTitleob.getTitlesPK().getFromDate(), titleob.getTitlesPK().getFromDate());
		System.out.println(p + " years"+p.get(ChronoUnit.YEARS));
		if (p.get(ChronoUnit.YEARS)<5 ) {
			throw new InvalidDataException("Not eligible for promotion. Can not change title");
		}
		
		previousTitleob.setToDate(titleob.getTitlesPK().getFromDate());
		titlesRepository.save(previousTitleob);
		
		titlesRepository.save(titleob);
		return "Employee is assigned   with title  from fromdate to enddate";
	}

	@Override
	public List<TitleProducerS> findByEmpNo(int empNo) throws RecordNotFoundException {
		List<TitleProducerS> titlesList=titlesRepository.findByEmpNo(empNo);
		if(titlesList==null || titlesList.isEmpty()) {
			throw new RecordNotFoundException("No titles Record for empNo = "+empNo);
		}
		return titlesList;
	}

	@Override
	public List<String> findDistinctIdByTitle() throws RecordNotFoundException {
		List<String> titlesList=titlesRepository.findDistinctIdByTitle();
		if(titlesList==null || titlesList.isEmpty()) {
			throw new RecordNotFoundException("No distinct titles Record");
		}
		return titlesList;
	}

	@Override
	public TitleProducerS findById(TitlesPK titlesPK) {
		Optional<TitleProducerS> opt=titlesRepository.findById(titlesPK);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			return null;
		}
	}

	
	

}
