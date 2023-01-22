package org.xpdojo.app.service;

public interface HelloService {
    String sayHello(String name);

    default int countOf(String name) {
        // HelloControllerTest에서 countOf(lambda expression)를 호출하면 에러
        return 0;
    }
}
