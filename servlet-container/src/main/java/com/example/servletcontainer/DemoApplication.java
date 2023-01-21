package com.example.servletcontainer;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *     ./gradlew bootRun
 * </pre>
 *
 * <pre>
 *     http -v :8080/hello
 * </pre>
 */
public class DemoApplication {

    public static void main(String[] args) {
        ServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.getWebServer(servletContext -> {
                    servletContext.addServlet("hello", new HttpServlet() {
                                @Override
                                protected void service(
                                        HttpServletRequest req,
                                        HttpServletResponse resp)
                                        throws ServletException, java.io.IOException {
                                    resp.getWriter().write("Hello World");
                                }
                            })
                            .addMapping("/hello");
                })
                .start();
    }

}
