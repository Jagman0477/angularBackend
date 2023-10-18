package com.jagman.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Person")
@Data
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(name = "userPassword")
	private String userPassword;
	
	@Column(name = "userName")
    private String userName;
    
	@Column(name = "contactNo")
	private long contactNo;
	
	@Column(name = "userRole")
    private String userRole;
	
	@Column(name = "userEmail")
    private String userEmail;
	
	@Column(name = "userAge")
    private int userAge;
	
	@Column(name = "gender")
    private String gender;
	
}
