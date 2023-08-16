package com.cg.security;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.cg.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

	private String username;

	@JsonIgnore
	private String password;

	
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl( int username,  String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.username = ""+username;
		this.password = password;
		this.authorities = authorities;
		
	}

	public static UserDetailsImpl build(Users user) {
		
	        List<GrantedAuthority> gr= new ArrayList<>();
	       
	        	 SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
	        	 gr.add(authority);
	        
	        		
		return new UserDetailsImpl(
				user.getUsername(), 
				user.getPassword(), 
				gr);
			
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}



	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(username, user.username);
	}
}
