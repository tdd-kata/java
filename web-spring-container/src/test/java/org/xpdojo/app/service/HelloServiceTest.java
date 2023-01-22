package org.xpdojo.app.service;

import org.junit.jupiter.api.Test;
import org.xpdojo.app.Hello;
import org.xpdojo.app.repository.HelloRepository;

import static org.assertj.core.api.Assertions.assertThat;

class HelloServiceTest {

    private static final HelloRepository helloRepository = new HelloRepository() {
        @Override
        public void increaseCount(String name) {
        }

        @Override
        public Hello findHello(String name) {
            return null;
        }
    };

    @Test
    void simpleHelloService() {
        SimpleHelloService simpleHelloService = new SimpleHelloService(helloRepository);

        String actual = simpleHelloService.sayHello("Spring");
        String expected = "Hello Spring!";

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);

        String actual = decorator.sayHello("Spring");
        String expected = "*Spring*";

        assertThat(actual).isEqualTo(expected);
    }

}
