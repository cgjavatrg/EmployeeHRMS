package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.InvalidDataException;
import com.cg.security.JwtRequestDTO;
import com.cg.security.JwtResponseDTO;
import com.cg.security.JwtUtil;
import com.cg.security.MyUserDetailsService;
import com.cg.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "*")
public class LoginController {
	
//	@GetMapping({"/hello" , "/demo"})
//	public String greet() {
//		return "Welcome to Login";
//	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping({"/employeehrmsconsumer/user/login","/adminhrmsconsumer/user/login"})
	public JwtResponseDTO login(@RequestBody JwtRequestDTO jwtdto) throws InvalidDataException  {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtdto.getUsername(), jwtdto.getPassword())
		);
		

		final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
				.loadUserByUsername(jwtdto.getUsername());
		
		String myrole=userDetails.getAuthorities().iterator().next().getAuthority();
		System.out.println(myrole);
		
		if(!myrole.equals(jwtdto.getRole())) {
			throw new InvalidDataException("Invalid Role!!!!");
		}

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		JwtResponseDTO udto=new JwtResponseDTO();
		udto.setToken(jwt);
		return udto;
		
	}
}
