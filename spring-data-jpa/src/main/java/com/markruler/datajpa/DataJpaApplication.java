package com.markruler.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication를 사용하면 아래 어노테이션 생략 가능
// @EnableJpaRepositories(basePackages = "com.markruler.datajpa.repository")
@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

}
