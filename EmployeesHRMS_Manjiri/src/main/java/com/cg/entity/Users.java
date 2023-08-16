package com.cg.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 *
 * @author manji
 */
@Entity
public class Users implements Serializable {

    @Id
    @Basic(optional = false)
    private Integer username;
    
    @Basic(optional = false)
    private String password;
    
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @JsonIgnore
    @JoinColumn(name = "username", referencedColumnName = "emp_no", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EmployeeProducerS employees;

    public Users() {
    }

    public Users(Integer username) {
        this.username = username;
    }

    public Users(Integer username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cg.entity.Users[ username=" + username + " ]";
    }
    
}

