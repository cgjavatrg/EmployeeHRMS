package com.cg.service;

import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.entity.DeptEmpPK;
import com.cg.entity.DeptEmpProducerS;
import com.cg.exception.RecordNotFoundException;
import com.cg.repository.DeptEmpRepository;

@Service("deptEmpService")
public class DeptEmpServiceImpl implements DeptEmpService{
	
	@Autowired
	DeptEmpRepository deptEmpRepository;
	
	@Override
	public List<DeptEmpProducerS> findByEmpNo(int empNo) throws RecordNotFoundException {
		List<DeptEmpProducerS> deptEmpList=deptEmpRepository.findByEmpNo(empNo);
		if(deptEmpList==null || deptEmpList.isEmpty()) {
			throw new RecordNotFoundException("No Matching DeptEmp exist for empNo = "+empNo);
		}
		return deptEmpList;
	}

	@Override
	public DeptEmpProducerS findByEmpNoAndMaxFromDate(int empNo) throws RecordNotFoundException {
		DeptEmpProducerS deptemp=deptEmpRepository.findByEmpNoAndMaxFromDate(empNo,empNo);
		if(deptemp==null) {
			throw new RecordNotFoundException("No such Record for empNo = "+empNo);
		}
		return deptemp;
	}

	@Override
	public DeptEmpProducerS findByDeptEmpPK(DeptEmpPK deptEmpPK)  {
		Optional<DeptEmpProducerS> opd=deptEmpRepository.findById(deptEmpPK);
		if(opd.isPresent()) {
			return opd.get();
		}
		else
		{
			return null;
		}
				
	}

	@Transactional( propagation = Propagation.REQUIRES_NEW,rollbackFor = { Exception.class })
	@Override
	public String assignDeptToEmp(DeptEmpProducerS deptEmp) throws RecordNotFoundException {
		DeptEmpProducerS previousDeptEmp=this.findByEmpNoAndMaxFromDate(deptEmp.getDeptEmpPK().getEmpNo());
		previousDeptEmp.setToDate(deptEmp.getFromDate());
		deptEmpRepository.save(previousDeptEmp);
		
		deptEmpRepository.save(deptEmp);
		return "Employee is assigned to the department successfully.";
	}

}
