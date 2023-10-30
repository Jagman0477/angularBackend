package com.jagman.controllers;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jagman.enumeration.Role;
import com.jagman.model.Person;
import com.jagman.repository.PersonRepository;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("PERSON_SERVICE/api")
public class PersonController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public PersonController(PasswordEncoder passwordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Autowired
	PersonRepository personRepository;
	
	@PostMapping("/register")	
	public ResponseEntity<String> createNewPerson(@RequestBody Person person) {
		
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
					.build();
			
			personRepository.save(personTemp);
			return ResponseEntity.status(HttpStatus.CREATED).body("User created -> true");
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User created -> false");
	}
	
	@GetMapping("/getRegisters")	
	public ResponseEntity<List<Person>> getAllUsers(){
		System.out.println();
		List<Person> users = new ArrayList<>();
		personRepository.findAll().forEach(users::add);
		
		return new ResponseEntity<List<Person>>(users, HttpStatus.OK);
	}
	
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
	public String deleteUser(@PathVariable long user_id){
		personRepository.deleteById(user_id);
		
		return "User "+user_id+" deleted successfully";
	}
}
