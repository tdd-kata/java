package org.xpdojo.webspringcontainer;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class DemoApplication {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    /**
     * DispatcherServlet은 ApplicationContextAware의 구현체다.
     * Spring Container가 DispatcherServlet에게 ApplicationContext를 주입한다.
     * DispatcherServlet은 ApplicationContext를 통해 Bean을 찾아서 처리한다.
     *
     * @see ApplicationContextAware#setApplicationContext(ApplicationContext)
     */
    public static void main(String[] args) {
        AnnotationConfigServletWebApplicationContext applicationContext =
                new AnnotationConfigServletWebApplicationContext() {
                    @Override
                    protected void onRefresh() {
                        super.onRefresh();

                        ServletWebServerFactory factory = this.getBean(ServletWebServerFactory.class);
                        DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                        // dispatcherServlet.setApplicationContext(this);

                        WebServer webServer = factory.getWebServer(servletContext -> {
                            servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                                    .addMapping("/*");
                        });

                        webServer.start();
                    }
                };

        applicationContext.register(DemoApplication.class);
        applicationContext.refresh();
    }

}
