package com.cg.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ManagerDetailsDTO {
	private Integer empNo;
	private String firstName;
	private String lastName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	private String deptName;
	
	public ManagerDetailsDTO() {
		
	}

	public ManagerDetailsDTO(Integer empNo, String firstName, String lastName, LocalDate birthDate, String deptName) {
		super();
		this.empNo = empNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "ManagerDetailsDTO [empNo=" + empNo + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthDate=" + birthDate + ", deptName=" + deptName + "]";
	}
	
	

}
