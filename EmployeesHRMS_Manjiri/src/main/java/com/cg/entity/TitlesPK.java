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
public class TitlesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "emp_no")
    private int empNo;
    
    @Basic(optional = false)
    private String title;
    
    @Basic(optional = false)
    @Column(name = "from_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    public TitlesPK() {
    }

    public TitlesPK(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        hash += (title != null ? title.hashCode() : 0);
        hash += (fromDate != null ? fromDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TitlesPK)) {
            return false;
        }
        TitlesPK other = (TitlesPK) object;
        if (this.empNo != other.empNo) {
            return false;
        }
        if ((this.title == null && other.title != null) || (this.title != null && !this.title.equals(other.title))) {
            return false;
        }
        if ((this.fromDate == null && other.fromDate != null) || (this.fromDate != null && !this.fromDate.equals(other.fromDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.TitlesPK[ empNo=" + empNo + ", title=" + title + ", fromDate=" + fromDate + " ]";
    }
    
}
