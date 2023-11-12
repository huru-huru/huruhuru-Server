package com.huruhuru.huruhuru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication
public class HuruhuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuruhuruApplication.class, args);
	}

}
