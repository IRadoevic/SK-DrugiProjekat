package com.raf.cinemaeurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CinemaEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaEurekaServiceApplication.class, args);
    }

}
