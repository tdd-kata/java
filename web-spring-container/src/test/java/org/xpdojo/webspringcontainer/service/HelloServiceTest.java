package org.xpdojo.webspringcontainer.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloServiceTest {

    @Test
    void simpleHelloService() {
        SimpleHelloService simpleHelloService = new SimpleHelloService();

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
