package com.example.rms.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
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
                    "Hussein",
                    "Abdi",
                    "amin@email.com",
                    "071237627",
                    List.of("Java", "Python", "C++"),
                    LocalDate.of(2000, JANUARY, 5)
            );

            Student ali = new Student(
                    "Ali",
                    "Lasty",
                    "Midz",
                    "ali@email.com",
                    "0797267133",
                    List.of("SQL", "PHP"),
                    LocalDate.of(2003, JANUARY, 5)
            );

            Student nima = new Student(
                    "Nima",
                    "Ali",
                    "Hosni",
                    "nima@email.com",
                    "0797371823",
                    List.of("JavaScript", "Python"),
                    LocalDate.of(1999, JANUARY, 5)
            );

            repository.saveAll(
                    List.of(amin, ali, nima)
            );
        };
    }
}
