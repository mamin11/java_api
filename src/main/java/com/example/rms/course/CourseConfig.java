package com.example.rms.course;

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

            Course media = new Course(
                    "Media Tech",
                    2
            );

            Course Laravel = new Course(
                    "Laravel",
                    2
            );

            Course springboot = new Course(
                    "Springboot",
                    1
            );

            Course Systems = new Course(
                    "Systems Design",
                    1
            );

            Course c = new Course(
                    "C++",
                    4
            );

            Course Python = new Course(
                    "Python",
                    1
            );

            Course Javascript = new Course(
                    "JavaScript",
                    2
            );

            Course PHP = new Course(
                    "PHP",
                    1
            );

            repository.saveAll(
                    List.of(AI, db, Java, media, Laravel, springboot, Systems, c, Python, Javascript, PHP)
            );
        };
    }
}
