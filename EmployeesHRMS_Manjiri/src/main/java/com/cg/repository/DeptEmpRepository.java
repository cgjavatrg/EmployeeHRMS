package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.DeptEmpPK;
import com.cg.entity.DeptEmpProducerS;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmpProducerS, DeptEmpPK> {
	
	@Query("select de from DeptEmpProducerS de where de.deptEmpPK.empNo=?1 order by de.fromDate")
	List<DeptEmpProducerS> findByEmpNo(int empNo);
	
	@Query("select de from DeptEmpProducerS de where de.deptEmpPK.empNo=?1 and de.fromDate=(select max(ds.fromDate) from DeptEmpProducerS ds where ds.deptEmpPK.empNo=?2)")
	DeptEmpProducerS findByEmpNoAndMaxFromDate(int empNo,int empNo1);
}
