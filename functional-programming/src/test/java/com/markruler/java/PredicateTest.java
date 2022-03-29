package com.markruler.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PredicateTest {

    @Nested
    @DisplayName("Predicate")
    class Describe_predicate {

        List<String> inventory = new ArrayList<>();

        @BeforeEach
        void setUp() {
            inventory.add("a");
            inventory.add(null);
            inventory.add("w");
            inventory.add("");
            inventory.add("   ");
            inventory.add("z");
        }

        @Test
        @DisplayName("익명 클래스를 사용한 Functional Interface")
        void sut_predicate_anonymous_class() {

            Predicate<String> nonEmptyStringPredicate = new Predicate<>() {
                @Override
                public boolean test(String s) {
                    return s != null && !s.isBlank();
                }
            };

            List<String> collect = inventory.stream().filter(nonEmptyStringPredicate).collect(Collectors.toList());

            assertThat(collect)
                    .hasSize(3)
                    .containsExactly("a", "w", "z");
        }

        @Test
        @DisplayName("람다 표현식을 사용한 Functional Interface")
        void sut_predicate_lambda() {

            Predicate<String> nonEmptyStringPredicate = s -> s != null && !s.isBlank();

            List<String> collect = inventory.stream().filter(nonEmptyStringPredicate).collect(Collectors.toList());

            assertThat(collect)
                    .hasSize(3)
                    .containsExactly("a", "w", "z");
        }

    }
}
