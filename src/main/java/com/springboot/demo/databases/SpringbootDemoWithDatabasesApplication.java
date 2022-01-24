package com.springboot.demo.databases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootDemoWithDatabasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoWithDatabasesApplication.class, args);
	}
}
