package com.jagman.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("PERSON_SERVICE/auth")
public class Hello {

	@GetMapping("/test")
	public String hello() {
		return "Hello this is secured";
	}
	
}
