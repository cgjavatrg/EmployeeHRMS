//package com.cg.entity;
//import java.io.Serializable;
//import java.time.LocalDate;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import jakarta.persistence.Basic;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
///**
// *
// * @author manji
// */
//@Entity
//@Table(name = "dept_emp_latest_date")
//public class DeptEmpLatestDate implements Serializable {
//
//	@Basic(optional = false)
//    @Column(name = "emp_no")
//    private int empNo;
//    
//	@Column(name = "from_date")
//	@JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate fromDate;
//    
//	@Column(name = "to_date")
//	@JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate toDate;
//
//    public DeptEmpLatestDate() {
//    }
//
//    public int getEmpNo() {
//        return empNo;
//    }
//
//    public void setEmpNo(int empNo) {
//        this.empNo = empNo;
//    }
//
//    public LocalDate getFromDate() {
//        return fromDate;
//    }
//
//    public void setFromDate(LocalDate fromDate) {
//        this.fromDate = fromDate;
//    }
//
//    public LocalDate getToDate() {
//        return toDate;
//    }
//
//    public void setToDate(LocalDate toDate) {
//        this.toDate = toDate;
//    }
//    
//}
