package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.entity.DeptManagerPK;
import com.cg.entity.DeptManagerS;
import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;
import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.DeptManagerRepository;

@Service("deptManagerService")
public class DeptManagerServiceImpl implements DeptManagerService {
	@Autowired
	DeptManagerRepository deptManagerRepository;
	
	@Autowired
	TitlesService titlesService;
	
	@Override
	public List<DeptManagerS> findByDeptNo(String deptNo) throws RecordNotFoundException {
		List<DeptManagerS> deptmgrList=deptManagerRepository.findByDeptNo(deptNo);
		if(deptmgrList==null || deptmgrList.isEmpty()) {
			throw new RecordNotFoundException("No Matching Department Manager record exist for deptNo = "+deptNo);
		}
		return deptmgrList;
	}
	
	@Override
	public DeptManagerS findByDeptManagerPK(DeptManagerPK deptManagerPK) {
		Optional<DeptManagerS> opdm=deptManagerRepository.findById(deptManagerPK);
		if(opdm.isPresent()) {
			return opdm.get();
		}
		else {
			return null;
		}
	}
	

	@Override
	public DeptManagerS findByDeptNoAndMaxFromDate(String deptNo) throws RecordNotFoundException {
		
		DeptManagerS deptmgr=deptManagerRepository.findByDeptNoAndMaxFromDate(deptNo, deptNo);
		if(deptmgr==null) {
			throw new RecordNotFoundException("No such Record for deptNo = "+deptNo);
		}
		return deptmgr;
	}

	@Transactional( propagation = Propagation.REQUIRES_NEW,rollbackFor = { Exception.class })
	@Override
	public String assignManagerToDept(DeptManagerS deptmgr) throws RecordNotFoundException, InvalidDataException {
		DeptManagerS previousDeptMgr= this.findByDeptNoAndMaxFromDate(deptmgr.getDeptManagerPK().getDeptNo());
		previousDeptMgr.setToDate(deptmgr.getFromDate());
		deptManagerRepository.save(previousDeptMgr);
		
		TitleProducerS ob=new TitleProducerS();
		TitlesPK tpk=new TitlesPK(deptmgr.getDeptManagerPK().getEmpNo(), "Manager", deptmgr.getFromDate());
		ob.setTitlesPK(tpk);
		ob.setToDate(deptmgr.getToDate());
		titlesService.assignTitleToEmployee(ob);
		
		deptManagerRepository.save(deptmgr);
		return "Employee is assigned  as manager for department successfully.";
	}





	
	
}
