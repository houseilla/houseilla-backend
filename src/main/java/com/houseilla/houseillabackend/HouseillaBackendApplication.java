package com.houseilla.houseillabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


// Excludes the default auto-configuration engine to stop random password alerts on console boot
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class HouseillaBackendApplication {


	public static void main(String[] args) {

		SpringApplication.run(HouseillaBackendApplication.class, args);
	}

}
