package com.cg.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 *
 * @author manji
 */
@Entity
@Table(name = "current_dept_emp")
@Immutable
public class CurrentDeptEmp implements Serializable {
   
	@Id
	@Column(name = "emp_no")
    private int empNo;

    @Column(name = "dept_no")
    private String deptNo;
    
    @Column(name = "from_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
  
    @Column(name = "to_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    public CurrentDeptEmp() {
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

	@Override
	public String toString() {
		return "CurrentDeptEmp [empNo=" + empNo + ", deptNo=" + deptNo + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ "]";
	}
    
}
