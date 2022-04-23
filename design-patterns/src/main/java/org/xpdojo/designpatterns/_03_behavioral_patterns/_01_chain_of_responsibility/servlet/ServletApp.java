package org.xpdojo.designpatterns._03_behavioral_patterns._01_chain_of_responsibility.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ServletApp {

    public static void main(String[] args) {
        SpringApplication.run(ServletApp.class, args);
    }
}
