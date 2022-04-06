package com.markruler.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class BiFunctionTest {

    @Test
    @DisplayName("1개의 인자를 받아 1개의 값을 반환하는 FunctionalInterface")
    void test_Function() {
        Function<Integer, Integer> add = a -> a + 1;
        assertThat(add.apply(1)).isEqualTo(2);
    }

    @Test
    @DisplayName("2개의 인자를 받아 1개의 값을 반환하는 FunctionalInterface")
    void test_BiFunction() {
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        assertThat(add.apply(3, 6)).isEqualTo(9);
    }

}
