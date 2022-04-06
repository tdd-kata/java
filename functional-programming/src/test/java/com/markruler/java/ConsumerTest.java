package com.markruler.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

class ConsumerTest {

    @Test
    @DisplayName("Consumer는 리턴값이 없다")
    void sould_return_nothing() {
        Consumer<String> myConsumer = s -> {
            System.out.println(s);
        };

        myConsumer.accept("Testing Simple Consumer");
    }
}
