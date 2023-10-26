package com.jagman.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jagman.config.JwtHelper;
import com.jagman.config.TestAuthenticationManager;
import com.jagman.model.Login;
import com.jagman.model.Person;
import com.jagman.repository.PersonRepository;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("PERSON_SERVICE/api")
public class LoginController {
	
	@Autowired
	PersonRepository personRepository;
	
	private static AuthenticationManager testAuthenticationManager = new TestAuthenticationManager();
	
	@Autowired
	private JwtHelper jwtHelper;
	
//	public LoginController(AuthenticationManager authenticationManager){
//		this.authenticationManager = authenticationManager;
//	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody Login login) throws Exception {
		
		Optional<Person> existingUser = personRepository.findByuserEmail(login.getUserEmail());
		String hashedPass = "";
		
		if(existingUser.isPresent()) {
			hashedPass += existingUser.get().getUserPassword();
		}
		
		try {
			Authentication req = new UsernamePasswordAuthenticationToken(hashedPass, login.getUserPassword());
			Authentication res = testAuthenticationManager.authenticate(req);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Optional<Person> userDetails;
		
		try {
			userDetails = personRepository.findByuserEmail(login.getUserEmail());
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		String jwt = jwtHelper.generateToken(userDetails.get().getUserEmail());
		
		return ResponseEntity.ok(jwt);
		
	}
	
}
