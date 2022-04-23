package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorTest {

    @Test
    void sut_comparator() {

        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(5);

        Collections.sort(numbers, Comparator.naturalOrder());
        assertThat(numbers).containsExactly(5, 10);

        numbers.sort(Comparator.reverseOrder());
        assertThat(numbers).containsExactly(10, 5);
    }
}
