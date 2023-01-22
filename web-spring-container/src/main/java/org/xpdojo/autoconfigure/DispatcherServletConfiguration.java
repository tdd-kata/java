package org.xpdojo.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

// @Configuration
public class DispatcherServletConfiguration {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

}
