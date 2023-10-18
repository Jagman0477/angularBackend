package com.jagman.controllers;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jagman.model.Person;
import com.jagman.repository.PersonRepository;

@RestController
@RequestMapping("/api")
public class PersonController {

	@Autowired
	PersonRepository personRepository;
	
	@PostMapping("/register")
	public String createNewPerson(@RequestBody Person person) {
		if(!getUserByEmail(person.getUserEmail())) {
			personRepository.save(person);
			return "Person created in database";
		}
		return "Email already exists";
	}
	
	@GetMapping("/register")
	public ResponseEntity<List<Person>> getAllUsers(){
		List<Person> users = new ArrayList<>();
		personRepository.findAll().forEach(users::add);
		
		return new ResponseEntity<List<Person>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/register/{user_email}")
	public boolean getUserByEmail(@PathVariable String user_email){
		Optional<Person> user = personRepository.findByuserEmail(user_email);
		if(user.isPresent())
			return true;	
		else 
			return false;
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
