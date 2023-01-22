package org.xpdojo.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xpdojo.container.MySpringBootApplication;

import javax.annotation.PostConstruct;

@MySpringBootApplication
public class DemoApplication {

    private final JdbcTemplate jdbcTemplate;

    public DemoApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("create table if not exists hello (name varchar(50), count int)");
    }

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
