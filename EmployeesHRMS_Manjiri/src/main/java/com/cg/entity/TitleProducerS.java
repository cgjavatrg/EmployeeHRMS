package com.cg.entity;
import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="Titles")
public class TitleProducerS implements Serializable {
    @EmbeddedId
    protected TitlesPK titlesPK;
    
    @Column(name = "to_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private EmployeeProducerS employees;

    public TitleProducerS() {
    }

    public TitleProducerS(TitlesPK titlesPK) {
        this.titlesPK = titlesPK;
    }

    public TitleProducerS(int empNo, String title, LocalDate fromDate) {
        this.titlesPK = new TitlesPK(empNo, title, fromDate);
    }

    public TitlesPK getTitlesPK() {
        return titlesPK;
    }

    public void setTitlesPK(TitlesPK titlesPK) {
        this.titlesPK = titlesPK;
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
        hash += (titlesPK != null ? titlesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TitleProducerS)) {
            return false;
        }
        TitleProducerS other = (TitleProducerS) object;
        if ((this.titlesPK == null && other.titlesPK != null) || (this.titlesPK != null && !this.titlesPK.equals(other.titlesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.TitleProducerS[ titlesPK=" + titlesPK + " ]";
    }
    
}
