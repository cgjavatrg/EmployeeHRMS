package com.cg.entity;



import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 *
 * @author manji
 */
@Entity
@Table(name = "current_emp_dept_salary")
public class CurrentEmpDeptSalary implements Serializable {

	@Id
	@Basic(optional = false)
    @Column(name = "emp_no")
    private int empNo;
	
    @Basic(optional = false)
    @Column(name = "salary")
    private int salary;
    
    @Column(name = "from_date")
    private LocalDate fromDate;
    
    @Column(name = "to_date")
     private LocalDate toDate;
    
    @Basic(optional = false)
    @Column(name = "dept_no")
    private String deptNo;
    
    @Basic(optional = false)
    @Column(name = "dept_name")
    private String deptName;

    public CurrentEmpDeptSalary() {
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
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

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
}
