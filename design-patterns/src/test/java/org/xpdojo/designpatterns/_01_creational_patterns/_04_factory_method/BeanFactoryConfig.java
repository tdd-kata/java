package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactoryConfig {

    @Bean
    public String hi() {
        return "hello";
    }

}
