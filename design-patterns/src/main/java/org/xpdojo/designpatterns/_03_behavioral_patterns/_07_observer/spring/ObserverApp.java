package org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObserverApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ObserverApp.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
