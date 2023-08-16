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
@Table(name = "dept_manager")
public class DeptManagerS implements Serializable {
    @EmbeddedId
    protected DeptManagerPK deptManagerPK;
    
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

    public DeptManagerS() {
    }

    public DeptManagerS(DeptManagerPK deptManagerPK) {
        this.deptManagerPK = deptManagerPK;
    }

    public DeptManagerS(DeptManagerPK deptManagerPK, LocalDate fromDate, LocalDate toDate) {
        this.deptManagerPK = deptManagerPK;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptManagerS(int empNo, String deptNo) {
        this.deptManagerPK = new DeptManagerPK(empNo, deptNo);
    }

    public DeptManagerPK getDeptManagerPK() {
        return deptManagerPK;
    }

    public void setDeptManagerPK(DeptManagerPK deptManagerPK) {
        this.deptManagerPK = deptManagerPK;
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
        hash += (deptManagerPK != null ? deptManagerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeptManagerS)) {
            return false;
        }
        DeptManagerS other = (DeptManagerS) object;
        if ((this.deptManagerPK == null && other.deptManagerPK != null) || (this.deptManagerPK != null && !this.deptManagerPK.equals(other.deptManagerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.DeptManagerS[ deptManagerPK=" + deptManagerPK + " ]";
    }
    
}
