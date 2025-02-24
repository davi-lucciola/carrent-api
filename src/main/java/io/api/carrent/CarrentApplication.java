package io.api.carrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CarrentApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarrentApplication.class, args);
	}
}
