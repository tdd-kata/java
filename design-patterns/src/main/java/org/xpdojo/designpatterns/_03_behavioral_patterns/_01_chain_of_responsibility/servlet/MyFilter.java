package org.xpdojo.designpatterns._03_behavioral_patterns._01_chain_of_responsibility.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * http://localhost:8080/hello
 */
@WebFilter(urlPatterns = "/hello")
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("pre-myfilter");
        chain.doFilter(request, response);
        System.out.println("post-myfilter");
    }
}
