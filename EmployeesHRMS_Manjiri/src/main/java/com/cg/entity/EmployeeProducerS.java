package com.cg.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author manji
 */
@Entity
@Table(name="Employees")
public class EmployeeProducerS implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "emp_no")
    private Integer empNo;
    
    @Basic(optional = false)
    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    
    @Basic(optional = false)
    private Character gender;
    
    @Basic(optional = false)
    @Column(name = "hire_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<SalarieProducerS> salariesCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<DeptEmpProducerS> deptEmpCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<DeptManagerS> deptManagerCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<TitleProducerS> titlesCollection;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employees")
    private Users users;

    public EmployeeProducerS() {
    }

    public EmployeeProducerS(Integer empNo) {
        this.empNo = empNo;
    }

    public EmployeeProducerS(Integer empNo, LocalDate birthDate, String firstName, String lastName, Character gender, LocalDate hireDate) {
        this.empNo = empNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Collection<SalarieProducerS> getSalariesCollection() {
        return salariesCollection;
    }

    public void setSalariesCollection(Collection<SalarieProducerS> salariesCollection) {
        this.salariesCollection = salariesCollection;
    }

    
    public Collection<DeptEmpProducerS> getDeptEmpCollection() {
        return deptEmpCollection;
    }

    public void setDeptEmpCollection(Collection<DeptEmpProducerS> deptEmpCollection) {
        this.deptEmpCollection = deptEmpCollection;
    }

    public Collection<DeptManagerS> getDeptManagerCollection() {
        return deptManagerCollection;
    }

    public void setDeptManagerCollection(Collection<DeptManagerS> deptManagerCollection) {
        this.deptManagerCollection = deptManagerCollection;
    }

    public Collection<TitleProducerS> getTitlesCollection() {
        return titlesCollection;
    }

    public void setTitlesCollection(Collection<TitleProducerS> titlesCollection) {
        this.titlesCollection = titlesCollection;
    }

    
    public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (empNo != null ? empNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeProducerS)) {
            return false;
        }
        EmployeeProducerS other = (EmployeeProducerS) object;
        if ((this.empNo == null && other.empNo != null) || (this.empNo != null && !this.empNo.equals(other.empNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.EmployeeProducerS[ empNo=" + empNo + " ]";
    }
    
}
