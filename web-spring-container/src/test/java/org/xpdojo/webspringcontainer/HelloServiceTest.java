package org.xpdojo.webspringcontainer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloServiceTest {

    @Test
    void helloService() {
        assertThat(new SimpleHelloService().sayHello("Spring"))
                .isEqualTo("Hello Spring!");
    }

}
