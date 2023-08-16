package com.cg.entity;
import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


/**
 *
 * @author manji
 */
@Embeddable
public class SalariesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "emp_no")
    private int empNo;
    
    @Basic(optional = false)
    @Column(name = "from_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    public SalariesPK() {
    }

    public SalariesPK(int empNo, LocalDate fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empNo;
        hash += (fromDate != null ? fromDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalariesPK)) {
            return false;
        }
        SalariesPK other = (SalariesPK) object;
        if (this.empNo != other.empNo) {
            return false;
        }
        if ((this.fromDate == null && other.fromDate != null) || (this.fromDate != null && !this.fromDate.equals(other.fromDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.SalariesPK[ empNo=" + empNo + ", fromDate=" + fromDate + " ]";
    }
    
}
