package com.markruler.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FunctionalInterface 를 직접 만들 수 있다")
class FunctionalInterfaceTest {

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    @Test
    @DisplayName("TriFunction 인터페이스를 직접 정의할 수 있다")
    void test_custom_FunctionalInterface() {
        TriFunction<Integer, Integer, Integer, Integer> triFunction = (t, u, v) -> t + u + v;
        assertThat(triFunction.apply(1, 2, 3)).isEqualTo(6);
    }
}
