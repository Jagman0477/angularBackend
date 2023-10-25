package com.jagman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HealthInsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthInsuranceApplication.class, args);
	}

}
