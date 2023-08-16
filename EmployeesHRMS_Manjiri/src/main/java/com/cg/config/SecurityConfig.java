package com.cg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cg.security.AuthEntryPointJwt;
import com.cg.security.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	
	 @Bean
	 public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(myUserDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	 
	   @Bean
	   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
				// TODO Auto-generated method stub
				http.csrf().disable()
				.authorizeHttpRequests().
				requestMatchers( "/swagger-ui/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/api/v1/adminhrmsconsumer/user/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/api/v1/employeehrmsconsumer/user/**").permitAll()

				.requestMatchers(HttpMethod.GET,"/api/v1/adminhrmsconsumer/employees/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.GET,"/api/v1/deptemp/empno/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST,"/api/v1/adminhrmsconsumer/assigndept").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.GET,"/api/v1/deptmanager/deptNo/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST,"/api/v1/adminpayrollconsumer/assignmgr").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST,"/api/v1/adminhrmsconsumer/assigntitle").hasAuthority("ADMIN")
				
				.requestMatchers(HttpMethod.GET,"/api/v1/employeehrmsconsumer/manager/**").hasAuthority("EMPLOYEE")
				.requestMatchers(HttpMethod.GET,"/api/v1/employeehrmsconsumer/employees/department/**").hasAuthority("EMPLOYEE")
				.requestMatchers(HttpMethod.GET,"/api/v1/employeehrmsconsumer/designations").hasAuthority("EMPLOYEE")
				
				.requestMatchers(HttpMethod.GET,"/api/v1/departments").hasAnyAuthority("ADMIN","EMPLOYEE")
				.requestMatchers(HttpMethod.GET,"/api/v1/titles/**").hasAnyAuthority("ADMIN","EMPLOYEE")
				
				.anyRequest()
				.authenticated()		
				.and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and()
			    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
				http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
				http.cors();
				
				return http.build();
			}

}
