package org.xpdojo.app.service;

import org.xpdojo.app.repository.HelloRepository;
import org.xpdojo.container.MyComponent;

@MyComponent
public class SimpleHelloService implements HelloService {

    private final HelloRepository helloRepository;

    public SimpleHelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Override
    public String sayHello(String name) {
        helloRepository.increaseCount(name);

        return "Hello " + name + "!";
    }

    @Override
    public int countOf(String name) {
        return helloRepository.countOf(name);
    }
}
