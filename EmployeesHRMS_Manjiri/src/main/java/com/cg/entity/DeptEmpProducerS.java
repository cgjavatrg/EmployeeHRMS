package com.cg.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 *
 * @author manji
 */
@Entity
@Table(name = "dept_emp")
public class DeptEmpProducerS implements Serializable {

    @EmbeddedId
    protected DeptEmpPK deptEmpPK;
    
    @Basic(optional = false)
    @Column(name = "from_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
    
    @Basic(optional = false)
    @Column(name = "to_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private DepartmentProducerS departments;
    
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private EmployeeProducerS employees;

    public DeptEmpProducerS() {
    }

    public DeptEmpProducerS(DeptEmpPK deptEmpPK) {
        this.deptEmpPK = deptEmpPK;
    }

    public DeptEmpProducerS(DeptEmpPK deptEmpPK, LocalDate fromDate, LocalDate toDate) {
        this.deptEmpPK = deptEmpPK;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptEmpProducerS(int empNo, String deptNo) {
        this.deptEmpPK = new DeptEmpPK(empNo, deptNo);
    }

    public DeptEmpPK getDeptEmpPK() {
        return deptEmpPK;
    }

    public void setDeptEmpPK(DeptEmpPK deptEmpPK) {
        this.deptEmpPK = deptEmpPK;
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

    public DepartmentProducerS getDepartments() {
        return departments;
    }

    public void setDepartments(DepartmentProducerS departments) {
        this.departments = departments;
    }

    public EmployeeProducerS getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeeProducerS employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deptEmpPK != null ? deptEmpPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeptEmpProducerS)) {
            return false;
        }
        DeptEmpProducerS other = (DeptEmpProducerS) object;
        if ((this.deptEmpPK == null && other.deptEmpPK != null) || (this.deptEmpPK != null && !this.deptEmpPK.equals(other.deptEmpPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.DeptEmpProducerS[ deptEmpPK=" + deptEmpPK + " ]";
    }
    
}
