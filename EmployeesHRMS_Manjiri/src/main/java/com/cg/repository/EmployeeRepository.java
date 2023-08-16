package com.cg.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.EmployeeDTO;
import com.cg.entity.EmployeeDeptDTO;
import com.cg.entity.EmployeeProducerS;
import com.cg.entity.ManagerDetailsDTO;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeProducerS, Integer>{
	@Query("select new com.cg.entity.EmployeeDTO(e.empNo,e.firstName,e.lastName,e.hireDate) from EmployeeProducerS e where e.hireDate between ?1 and ?2")
	List<EmployeeDTO> findByHireDateBetween(LocalDate startHireDate, LocalDate endHireDate);
	
	@Query("select new com.cg.entity.EmployeeDTO(e.empNo,e.firstName,e.lastName,e.hireDate) from EmployeeProducerS e where e.hireDate>?1")
	List<EmployeeDTO> findByHireDateGreaterThan(LocalDate hireDate);
	
	@Query("select new com.cg.entity.ManagerDetailsDTO(e.empNo,e.firstName,e.lastName,e.birthDate,d.deptName) from EmployeeProducerS e JOIN e.deptManagerCollection dm JOIN dm.departments d")
	List<ManagerDetailsDTO> findManagerDetails();
	
	@Query("select new com.cg.entity.EmployeeDeptDTO(e.empNo,e.firstName,e.lastName,de.fromDate,de.toDate,d.deptName) from EmployeeProducerS e JOIN e.deptEmpCollection  de JOIN de.departments d where de.deptEmpPK.deptNo=?1 and de.fromDate between ?2 and ?3")
	List<EmployeeDeptDTO> findByDeptAndFromDate(String deptNo, LocalDate starDate ,LocalDate endDate);
	
	@Query("select new com.cg.entity.EmployeeDTO(e.empNo,e.firstName,e.lastName,e.hireDate) from EmployeeProducerS e JOIN  e.deptManagerCollection dm where dm.fromDate>?1")
	List<EmployeeDTO> findByFormDateGreaterThan(LocalDate fromDate);
}
