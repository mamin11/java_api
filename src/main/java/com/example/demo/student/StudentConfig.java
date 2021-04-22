package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.JANUARY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository)
    {
        return args -> {
            Student amin = new Student(
                    "Amin",
                    "amin@email.com",
                    LocalDate.of(2000, JANUARY, 5)
            );

            Student ali = new Student(
                    "Ali",
                    "ali@email.com",
                    LocalDate.of(2003, JANUARY, 5)
            );

            Student nima = new Student(
                    "Nima",
                    "nima@email.com",
                    LocalDate.of(1999, JANUARY, 5)
            );

            repository.saveAll(
                    List.of(amin, ali, nima)
            );
        };
    }
}
