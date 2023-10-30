package com.jagman.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestAuthenticationManager implements AuthenticationManager{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

//	BCryptPasswordEncoder bcyBCryptPasswordEncoder = new BCryptPasswordEncoder();
//	
//	@Override
//	public Authentication authenticate(Authentication auth) throws AuthenticationException {
//			if(bcyBCryptPasswordEncoder.matches((CharSequence) auth.getCredentials(), auth.getName())) {
//				System.out.println("Hello");
//				return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials());
//				}
//			throw new BadCredentialsException("Password does not match");
//	}

}
