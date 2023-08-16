package com.cg.security;

public class JwtResponseDTO {
	private String token;
	public JwtResponseDTO() {
		// TODO Auto-generated constructor stub
	}
	public JwtResponseDTO(String token) {
		super();
		this.token = token;
	}
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "JwtResponseDTO [token=" + token + "]";
	}
	
	

}
