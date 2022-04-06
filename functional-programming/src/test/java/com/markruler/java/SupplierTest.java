package com.markruler.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class SupplierTest {

    @Test
    @DisplayName("String을 반환하는 Supplier")
    void should_return_string() {
        final String helloWorld = "Hello World!";
        final Supplier<String> stringSupplier = () -> helloWorld;

        assertThat(stringSupplier.get()).isEqualTo(helloWorld);
    }

    @RepeatedTest(10)
    @DisplayName("Random 값을 반환하는 Supplier")
    void should_return_random_double() {
        final Supplier<Double> randomSupplier = Math::random;

        System.out.println(randomSupplier.get());
        assertThat(randomSupplier.get()).isBetween(0.0, 1.0);
    }
}
