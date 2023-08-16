package com.cg.entity;
import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author manji
 */
@Embeddable
public class DeptManagerPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "emp_no")
    private int empNo;
    
    @Basic(optional = false)
    @Column(name = "dept_no")
    private String deptNo;

    public DeptManagerPK() {
    }

    public DeptManagerPK(int empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empNo;
        hash += (deptNo != null ? deptNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeptManagerPK)) {
            return false;
        }
        DeptManagerPK other = (DeptManagerPK) object;
        if (this.empNo != other.empNo) {
            return false;
        }
        if ((this.deptNo == null && other.deptNo != null) || (this.deptNo != null && !this.deptNo.equals(other.deptNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.DeptManagerPK[ empNo=" + empNo + ", deptNo=" + deptNo + " ]";
    }
    
}
