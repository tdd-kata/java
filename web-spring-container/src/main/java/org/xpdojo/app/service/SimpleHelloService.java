package org.xpdojo.app.service;

import org.xpdojo.container.MyComponent;

@MyComponent
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
