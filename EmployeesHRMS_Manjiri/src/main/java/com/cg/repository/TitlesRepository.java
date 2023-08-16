package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.cg.entity.TitleProducerS;
import com.cg.entity.TitlesPK;

@Repository
public interface TitlesRepository extends JpaRepository<TitleProducerS, TitlesPK>{

	@Query("select t from TitleProducerS t where t.titlesPK.empNo=?1 order by t.titlesPK.fromDate")
	List<TitleProducerS> findByEmpNo(int empNo);
	
	@Query("select t from TitleProducerS t where t.titlesPK.empNo=?1 and t.titlesPK.fromDate=(select max(t1.titlesPK.fromDate) from TitleProducerS t1 where t1.titlesPK.empNo=?2)")
	TitleProducerS findByEmpNoAndMaxFromDate(int empNo,int empNo1);
	
	@Query("SELECT DISTINCT t.titlesPK.title FROM TitleProducerS t")
	List<String> findDistinctIdByTitle();
}
