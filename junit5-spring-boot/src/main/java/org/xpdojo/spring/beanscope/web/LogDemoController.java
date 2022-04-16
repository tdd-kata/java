package org.xpdojo.spring.beanscope.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogDemoController {

    /**
     * Caused by: org.springframework.beans.factory.support.ScopeNotActiveException:
     * Error creating bean with name 'myLogger':
     * Scope 'request' is not active for the current thread;
     * consider defining a scoped proxy for this bean if you intend to refer to it from a singleton;
     * nested exception is java.lang.IllegalStateException:
     * No thread-bound request found:
     * Are you referring to request attributes outside of an actual web request,
     * or processing a request outside of the originally receiving thread?
     * If you are actually operating within a web request and still receive this message,
     * your code is probably running outside of DispatcherServlet:
     * In this case, use RequestContextListener or RequestContextFilter to expose the current request.
     */
    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
    private final MyLoggerProxy myLoggerProxy;
    private final LogDemoService logDemoService;

    @Autowired
    private ApplicationContext applicationContext;

    public LogDemoController(ObjectProvider<MyLogger> myLoggerObjectProvider, MyLoggerProxy myLoggerProxy, LogDemoService logDemoService) {
        this.myLoggerObjectProvider = myLoggerObjectProvider;
        this.myLoggerProxy = myLoggerProxy;
        this.logDemoService = logDemoService;
    }

    @GetMapping("demo")
    public String demo(HttpServletRequest servletRequest) {
        final String requestURL = servletRequest.getRequestURL().toString();

        myLoggerObjectProvider.ifAvailable(myLogger -> {
            myLogger.setRequestURL(requestURL);
            myLogger.log("MyLogger method called by LogDemoController");
        });

        myLoggerProxy.setRequestURL(requestURL);
        myLoggerProxy.log("MyLoggerProxy method called by LogDemoController");

        MyLoggerProxy proxy = applicationContext.getBean("myLoggerProxy", MyLoggerProxy.class);
        System.out.println("myLoggerProxy - " + proxy.getClass());
        // class org.xpdojo.spring.beanscope.web.MyLoggerProxy$$EnhancerBySpringCGLIB$$7d0c6573

        return logDemoService.demo();
    }
}
