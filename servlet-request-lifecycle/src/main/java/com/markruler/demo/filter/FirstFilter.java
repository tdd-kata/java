package com.markruler.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
// @org.springframework.core.annotation.Order(1)
public class FirstFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(FirstFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("Request first filter...{}", request.getServerName());

        chain.doFilter(request, response);

        log.info("Response first filter...{}", response.getCharacterEncoding());
    }
}
