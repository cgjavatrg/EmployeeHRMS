package com.cg.service;

import java.util.List;

import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;

public interface TitlesService {
	List<TitleProducerS> findByEmpNo(int empNo) throws RecordNotFoundException;
	List<String> findDistinctIdByTitle() throws RecordNotFoundException;
	
	TitleProducerS findById(TitlesPK titlesPK);
	
	TitleProducerS findByEmpNoAndMaxFromDate(int empNo) throws RecordNotFoundException;
	String assignTitleToEmployee(TitleProducerS titleob) throws RecordNotFoundException, InvalidDataException;
}
