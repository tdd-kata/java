package com.markruler.puzzler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("어려운 퍼즐")
class Puzzlers9AdvancedTest {

    @Nested
    @DisplayName("Puzzle 86: 괄호의 함정")
    class Describe_Poison_Paren_Litter {

        @Test
        @DisplayName("괄호를 추가하면 오류가 발생하는 표현식")
        void illegal_by_parenthesizing() {
            assertThat(-2147483648).isEqualTo(-2147483648);
            // 컴파일 에러
            // assertThat(-(2147483648)).isEqualTo(-2147483648);
        }
    }

}
