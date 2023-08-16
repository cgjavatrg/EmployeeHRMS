package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.DeptEmpProducerS;
import com.cg.entity.DeptManagerPK;
import com.cg.entity.DeptManagerS;

@Repository
public interface DeptManagerRepository extends JpaRepository<DeptManagerS, DeptManagerPK> {
	
	@Query("select de from DeptManagerS de where de.deptManagerPK.deptNo=?1 order by de.fromDate")
	List<DeptManagerS> findByDeptNo(String deptNo);
	
	@Query("select dm from DeptManagerS dm where dm.deptManagerPK.deptNo=?1 and dm.fromDate=(select max(dms.fromDate) from DeptManagerS dms where dms.deptManagerPK.deptNo=?2)")
	DeptManagerS findByDeptNoAndMaxFromDate(String deptNo,String deptNo1);
}
