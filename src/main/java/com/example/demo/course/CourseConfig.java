package com.example.demo.course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class CourseConfig {

    @Bean
    CommandLineRunner commandLineRunnerCourse(CourseRepository repository) {
        return args -> {
            Course AI = new Course(
                    "Artificial Intelligence",
                    3
            );

            Course db = new Course(
                    "Databases 3",
                    2
            );

            Course Java = new Course(
                    "Java",
                    3
            );

            repository.saveAll(
                    List.of(AI, db, Java)
            );
        };
    }
}
