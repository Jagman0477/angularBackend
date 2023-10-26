package com.jagman.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jagman.model.Person;
import com.jagman.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
public class userDetailsService implements UserDetailsService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public UserDetails loadUserByUsername(String user_email) throws UsernameNotFoundException {
		return personRepository.findByuserEmail(user_email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found "+user_email));
	}
	
}
