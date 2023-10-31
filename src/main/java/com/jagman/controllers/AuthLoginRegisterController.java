package com.jagman.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jagman.config.JwtHelper;
import com.jagman.config.TestAuthenticationManager;
import com.jagman.enumeration.Role;
import com.jagman.model.Login;
import com.jagman.model.Person;
import com.jagman.repository.PersonRepository;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("PERSON_SERVICE/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthLoginRegisterController {
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	private static AuthenticationManager testAuthenticationManager = new TestAuthenticationManager();
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	public LoginController(AuthenticationManager authenticationManager){
//		this.authenticationManager = authenticationManager;
//	}
	
	@GetMapping("/register/{user_email}")
	public Optional<Person> getUserByEmail(@PathVariable String user_email){
		Optional<Person> user = personRepository.findByuserEmail(user_email);
		if(user.isPresent())
			return user;	
		else 
			return null;
	}
	
	@GetMapping("/user/{user_id}")
	public ResponseEntity<Person> getUserById(@PathVariable long user_id){
		Optional<Person> user = personRepository.findById(user_id);
		if(user.isPresent())
			return new ResponseEntity<Person>(user.get(), HttpStatus.FOUND);	
		else 
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/user/{user_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable long user_id){
		personRepository.deleteById(user_id);
		
		return "User "+user_id+" deleted successfully";
	}

//	@PostMapping("/login")
//	public ResponseEntity<String> loginUser(@RequestBody Login login) throws Exception {
//		
//		Optional<Person> existingUser = personRepository.findByuserEmail(login.getUserEmail());
//		String hashedPass = "";
//		
//		if(existingUser.isPresent()) {
//			hashedPass += existingUser.get().getUserPassword();
//		}
//		
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserEmail(), login.getUserPassword())); 
//		if (authentication.isAuthenticated()) { 
//			var userDetails = personRepository.findByuserEmail(login.getUserEmail());
//			String jwt = jwtHelper.generateToken(userDetails.get().getUserEmail());
//			return ResponseEntity.ok(jwtHelper.generateToken(login.getUserEmail()));
//		} else { 
//			throw new UsernameNotFoundException("invalid user request !"); 
//		} 
		
//		try {
//			Authentication req = new UsernamePasswordAuthenticationToken(hashedPass, login.getUserPassword());
//			Authentication res = testAuthenticationManager.authenticate(req);
//		} catch (AuthenticationException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		
//		try {
//			
//			var userDetails = personRepository.findByuserEmail(login.getUserEmail());
//			String jwt = jwtHelper.generateToken(userDetails.get().getUserEmail());
//			return ResponseEntity.ok(jwt);
//			
//		} catch (UsernameNotFoundException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
		
//	}
	
	
	
}
