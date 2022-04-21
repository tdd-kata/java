package org.xpdojo.designpatterns._02_structural_patterns._01_adapter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListTest {

    @Test
    void sut_list() {
        List<String> strings = Arrays.asList("a", "b", "c");
        Enumeration<String> enumeration = Collections.enumeration(strings);
        ArrayList<String> list = Collections.list(enumeration);

        assertThat(list).containsExactly("a", "b", "c");
    }

}
