package com.jagman.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jagman.model.Person;
import com.jagman.repository.PersonRepository;

@Component
public class userDetailsService {

	@Autowired
	PersonRepository personRepository;
	
	public Optional<Person> loadUserByEmail(String user_email) {
		Optional<Person> user = personRepository.findByuserEmail(user_email);
		if(user.isPresent())
			return user;	
		else 
			return null;
	}
	
}
