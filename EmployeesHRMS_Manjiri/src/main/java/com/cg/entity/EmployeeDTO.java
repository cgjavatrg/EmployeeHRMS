package com.cg.entity;

import java.time.LocalDate;

public class EmployeeDTO {
	private Integer empNo;
	private String firstName;
	private String lastName;
	private LocalDate hireDate;
	public EmployeeDTO() {
		// TODO Auto-generated constructor stub
	}
	public EmployeeDTO(Integer empNo, String firstName, String lastName, LocalDate hireDate) {
		super();
		this.empNo = empNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireDate = hireDate;
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
	public LocalDate getHireDate() {
		return hireDate;
	}
	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}
	@Override
	public String toString() {
		return "EmployeeDTO [empNo=" + empNo + ", firstName=" + firstName + ", lastName=" + lastName + ", hireDate="
				+ hireDate + "]";
	}
	
	
}
