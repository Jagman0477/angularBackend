package com.jagman.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jagman.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
	
	Optional<Person> findByuserEmail(String user_email);
	
}
