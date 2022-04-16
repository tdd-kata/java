package org.xpdojo.spring.beanscope.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
    private final MyLoggerProxy myLoggerProxy;

    public LogDemoService(ObjectProvider<MyLogger> myLoggerObjectProvider, MyLoggerProxy myLoggerProxy) {
        this.myLoggerObjectProvider = myLoggerObjectProvider;
        this.myLoggerProxy = myLoggerProxy;
    }

    public String demo() {
        // 호출한 Controller와 같은 request scope이기 때문에 같은 UUID를 가진다.
        myLoggerObjectProvider.getIfAvailable().log("myLoggerObjectProvider method called by LogDemoService");

        myLoggerProxy.log("MyLoggerProxy method called by LogDemoService");

        return "demo";
    }

}
