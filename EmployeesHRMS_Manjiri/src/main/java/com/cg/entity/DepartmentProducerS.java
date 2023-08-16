package com.cg.entity;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author manji
 */
@Entity
@Table(name="Departments")
public class DepartmentProducerS implements Serializable {
    @Id
    @Column(name = "dept_no",nullable = false,unique=true)
    private String deptNo;
    
    @Column(name = "dept_name",nullable = false,unique = true)
    private String deptName;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departments")
    private Collection<DeptEmpProducerS> deptEmpCollection;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departments")
    private Collection<DeptManagerS> deptManagerCollection;

    public DepartmentProducerS() {
    }

    public DepartmentProducerS(String deptNo) {
        this.deptNo = deptNo;
    }

    public DepartmentProducerS(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deptNo != null ? deptNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartmentProducerS)) {
            return false;
        }
        DepartmentProducerS other = (DepartmentProducerS) object;
        if ((this.deptNo == null && other.deptNo != null) || (this.deptNo != null && !this.deptNo.equals(other.deptNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entiy.DepartmentProducerS[ deptNo=" + deptNo + " ]";
    }
    
}
