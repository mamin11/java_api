package com.example.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories(basePackages = {"com.example.demo.student", "com.example.demo.course"})
//scanBasePackages= {"com.example.demo.student", "com.example.demo.course"}
@SpringBootApplication()

		public class Rms {

	public static void main(String[] args) {
		SpringApplication.run(Rms.class, args);
	}

}
