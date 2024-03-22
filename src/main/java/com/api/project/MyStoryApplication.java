package com.api.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.api.project.model.entity")
public class MyStoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyStoryApplication.class, args);
	}

}