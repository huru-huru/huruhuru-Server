package com.huruhuru.huruhuru;

import com.huruhuru.huruhuru.global.config.S3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HuruhuruApplication {
//	static {
//		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
//	}

	public static void main(String[] args) {
		SpringApplication.run(HuruhuruApplication.class, args);
	}


}
