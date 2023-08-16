package com.cg.entity;

import java.time.LocalDate;

public class EmployeeDeptDTO {
	private Integer empNo;
	private String firstName;
	private String lastName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String deptName;
	
	public EmployeeDeptDTO() {

	}

	public EmployeeDeptDTO(Integer empNo, String firstName, String lastName, LocalDate fromDate, LocalDate toDate,
			String deptName) {
		super();
		this.empNo = empNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.deptName = deptName;
	}

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "EmployeeDeptDTO [empNo=" + empNo + ", firstName=" + firstName + ", lastName=" + lastName + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", deptName=" + deptName + "]";
	}
	
	
	
}
