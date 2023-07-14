package com.anabada.anabada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AnabadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnabadaApplication.class, args);
	}

}
