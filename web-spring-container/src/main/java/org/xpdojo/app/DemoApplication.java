package org.xpdojo.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.xpdojo.container.MySpringBootApplication;

@MySpringBootApplication
public class DemoApplication {

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            String serverType = environment.getProperty("server.type");
            System.out.println("server.type: " + serverType);
        };
    }

    public static void main(String[] args) {
        // MySpringApplication에서는 properties를 읽지 못함.
        // MySpringApplication.run(DemoApplication.class, args);
        SpringApplication.run(DemoApplication.class, args);
    }

}
