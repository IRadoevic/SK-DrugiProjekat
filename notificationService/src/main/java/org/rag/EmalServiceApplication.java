package org.rag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmalServiceApplication.class, args);
    }

}
