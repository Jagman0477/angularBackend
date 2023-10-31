package com.jagman.model;

public class jwtReturn {
	
	final String token;
	
	public jwtReturn(final String token) {
		this.token = token;
	}
	
	public String returnJWT(){
		return token;
	}
}
