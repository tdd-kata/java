package org.xpdojo.designpatterns._02_structural_patterns._07_proxy.spring;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.Bean;

// @org.springframework.boot.autoconfigure.SpringBootApplication
public class PerfApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PerfApp.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public ApplicationRunner applicationRunner(PerfService gameService) {
        return args -> gameService.start();
    }
}
