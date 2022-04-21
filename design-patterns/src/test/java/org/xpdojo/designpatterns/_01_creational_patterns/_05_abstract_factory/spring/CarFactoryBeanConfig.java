package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarFactoryBeanConfig {

    @Bean
    public CarFactory macFactory() {
        return new CarFactory();
    }
}
