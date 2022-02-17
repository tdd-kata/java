package com.markruler.plugin;

import com.markruler.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Nested
    @DisplayName("Unused variable")
    class Describe_Unused_varable {
        private int abc;
        private String ip = "127.0.0.1";

        @Test
        void shouldAnswerWithTrue()
        {
            String[] field = {"a", "b", "c", "d", "e"};
            assertThat(StringUtils.concatenate(field)).isEqualTo("abcde");
        }
    }
}
