package com.example.rms.module;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;

@Configuration
public class ModuleConfig {
//    @Bean
//    CommandLineRunner commandLineRunnerModules(ModuleRepository repository)
//    {
//        return args -> {
//            Module csy2020 = new Module(
//                    "csy2020",
//                    "Artificial Intelligence",
//                     "https://theoneamin.com",
//                    LocalDate.of(2020, JANUARY, 5)
//            );
//            Module csy2021 = new Module(
//                    "csy2021",
//                    "Databases",
//                    "",
//                    LocalDate.of(2020, FEBRUARY, 12)
//            );
//            Module csy2023 = new Module(
//                    "csy2023",
//                    "Databases",
//                    "https://theoneamin.com/portfolio",
//                    LocalDate.of(2020, FEBRUARY, 12)
//            );
//            Module csy3040 = new Module(
//                    "csy3040",
//                    "Media Tech",
//                    "https://theoneamin.com",
//                    LocalDate.of(2021, JANUARY, 8)
//            );
//
//            repository.saveAll(
//                    List.of(csy2020, csy2021, csy2023, csy3040)
//            );
//        };
//    }
}
