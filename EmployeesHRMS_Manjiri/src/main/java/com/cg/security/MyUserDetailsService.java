package com.cg.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.cg.entity.Users;
import com.cg.service.EmployeeService;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	EmployeeService employeeService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user=employeeService.findUserByname(username);
       if(user==null) {
    	   throw new UsernameNotFoundException("User with this username does not exists");
        }
       
       return UserDetailsImpl.build(user);
    }
}