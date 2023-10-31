package com.jagman.controllers;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.jagman.model.jwtReturn;
import com.jagman.repository.PersonRepository;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("PERSON_SERVICE/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ApiLoginRegisterController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public ApiLoginRegisterController(PasswordEncoder passwordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	private static AuthenticationManager testAuthenticationManager = new TestAuthenticationManager();
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	PersonRepository personRepository;
	
	
	@PostMapping("/register")	
	public ResponseEntity<Person> createNewPerson(@RequestBody Person person) {
		System.out.println(person);
		// Check if user exists
		if(getUserByEmail(person.getUserEmail()) == null) {
			
			// Hash user password
			String hashPassword = bCryptPasswordEncoder.encode(person.getUserPassword());
			
			var personTemp = Person.builder()
					.userAge(person.getUserAge())
					.userEmail(person.getUserEmail())
					.userName(person.getUsername())
					.userPassword(hashPassword)
					.userRole(Role.USER)
					.userGender(person.getUserGender())
					.build();
			
			personRepository.save(personTemp);
			return new ResponseEntity<Person>(person, HttpStatus.CREATED);
			//return ResponseEntity.status(HttpStatus.OK).body("User created -> true");
		}
		
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User created -> false");
		return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
	}
	
	public Optional<Person> getUserByEmail(@PathVariable String user_email){
		Optional<Person> user = personRepository.findByuserEmail(user_email);
		if(user.isPresent())
			return user;	
		else 
			return null;
	}
	
	@GetMapping("/getRegisters")	
	public ResponseEntity<List<Person>> getAllUsers(){
		System.out.println();
		List<Person> users = new ArrayList<>();
		personRepository.findAll().forEach(users::add);
		
		return new ResponseEntity<List<Person>>(users, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object>loginUser(@RequestBody Login login) throws Exception {
		
		System.out.println("This is login data: "+login);
		Optional<Person> existingUser = personRepository.findByuserEmail(login.getUserEmail());
		String hashedPass = "";
		
		if(existingUser.isPresent()) {
			hashedPass += existingUser.get().getUserPassword();
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserEmail(), login.getUserPassword())); 
		if (authentication.isAuthenticated()) { 
			var userDetails = personRepository.findByuserEmail(login.getUserEmail());
			String jwt = jwtHelper.generateToken(userDetails.get().getUserEmail());
			Map<String, Object> object = new HashMap<>();
			  object.put("jwt", jwt);
			 System.out.println(object+"                "+userDetails);
			return new ResponseEntity<Object>(object,  HttpStatus.ACCEPTED);
		} else { 
			throw new UsernameNotFoundException("invalid user request !"); 
		} 

	}
	
	
}