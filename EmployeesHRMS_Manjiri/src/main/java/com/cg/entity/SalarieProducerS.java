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
@Table(name="Salaries")
public class SalarieProducerS implements Serializable {

    @EmbeddedId
    protected SalariesPK salariesPK;
    
    @Basic(optional = false)
    private int salary;
    
    @Basic(optional = false)
    @Column(name = "to_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private EmployeeProducerS employees;

    public SalarieProducerS() {
    }

    public SalarieProducerS(SalariesPK salariesPK) {
        this.salariesPK = salariesPK;
    }

    public SalarieProducerS(SalariesPK salariesPK, int salary, LocalDate toDate) {
        this.salariesPK = salariesPK;
        this.salary = salary;
        this.toDate = toDate;
    }

    public SalarieProducerS(int empNo, LocalDate fromDate) {
        this.salariesPK = new SalariesPK(empNo, fromDate);
    }

    public SalariesPK getSalariesPK() {
        return salariesPK;
    }

    public void setSalariesPK(SalariesPK salariesPK) {
        this.salariesPK = salariesPK;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
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
        hash += (salariesPK != null ? salariesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalarieProducerS)) {
            return false;
        }
        SalarieProducerS other = (SalarieProducerS) object;
        if ((this.salariesPK == null && other.salariesPK != null) || (this.salariesPK != null && !this.salariesPK.equals(other.salariesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.SalarieProducerS[ salariesPK=" + salariesPK + " ]";
    }
    
}
