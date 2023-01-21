package com.example.servletcontainer;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

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

        WebServer webServer = factory.getWebServer(servletContext -> {
            HelloController helloController = new HelloController();

            servletContext.addServlet("front-controller", new HttpServlet() {
                        @Override
                        protected void service(
                                HttpServletRequest request,
                                HttpServletResponse response)
                                throws ServletException, IOException {
                            if (request.getRequestURI().equals("/hello")
                                    && request.getMethod().equals(GET.name())) {
                                // http -v GET ":8080/hello?name=World"
                                String name = request.getParameter("name");
                                response.setStatus(OK.value());
                                response.setHeader(CONTENT_TYPE, TEXT_PLAIN_VALUE);
                                response.getWriter().write(helloController.hello() + name);
                            } else if (request.getRequestURI().equals("/user")) {
                                //
                            } else {
                                // http -v POST ":8080/hello?name=World"
                                response.setStatus(NOT_FOUND.value());
                            }
                        }
                    })
                    .addMapping("/hello");
        });

        webServer.start();
    }

}
