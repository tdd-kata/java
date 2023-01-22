package org.xpdojo.app.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HelloControllerTest {

    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name);
        String actual = helloController.hello("Test");
        assertThat(actual).isEqualTo("Test");
    }

    @Test
    void failesHelloController() {
        HelloController helloController = new HelloController(name -> name);
        assertThatThrownBy(() -> {
            String hello = helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            String hello = helloController.hello(" ");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
