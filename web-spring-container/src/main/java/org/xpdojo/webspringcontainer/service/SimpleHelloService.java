package org.xpdojo.webspringcontainer.service;

import org.xpdojo.webspringcontainer.container.MyComponent;

@MyComponent
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
