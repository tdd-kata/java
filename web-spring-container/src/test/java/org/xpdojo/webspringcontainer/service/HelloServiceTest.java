package org.xpdojo.webspringcontainer.service;

import org.junit.jupiter.api.Test;
import org.xpdojo.webspringcontainer.service.SimpleHelloService;

import static org.assertj.core.api.Assertions.assertThat;

class HelloServiceTest {

    @Test
    void helloService() {
        assertThat(new SimpleHelloService().sayHello("Spring"))
                .isEqualTo("Hello Spring!");
    }

}
