package org.example.outsourcing_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OutSourcingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutSourcingProjectApplication.class, args);
    }

}
