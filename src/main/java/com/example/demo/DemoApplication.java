package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories(basePackages = {"com.example.demo.student", "com.example.demo.course"})
//scanBasePackages= {"com.example.demo.student", "com.example.demo.course"}
@SpringBootApplication()

		public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
