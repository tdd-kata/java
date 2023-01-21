package org.xpdojo.webspringcontainer;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class DemoApplication {

    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }

    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        AnnotationConfigServletWebApplicationContext applicationContext =
                new AnnotationConfigServletWebApplicationContext() {
                    @Override
                    protected void onRefresh() {
                        super.onRefresh();

                        ServletWebServerFactory factory = new TomcatServletWebServerFactory();

                        WebServer webServer = factory.getWebServer(servletContext -> {
                            servletContext.addServlet("dispatcherServlet",
                                            new DispatcherServlet(this)) // require GenericWebApplicationContext
                                    .addMapping("/*");
                        });

                        webServer.start();
                    }
                };

        applicationContext.register(DemoApplication.class);
        applicationContext.refresh();
    }

}
