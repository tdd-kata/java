package com.markruler.puzzler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("라이브러리 퍼즐")
class Puzzlers6LibraryTest {

    @Nested
    @DisplayName("Puzzle 56: 큰 문제")
    class Describe_Big_Problem {

        @Test
        @DisplayName("불변 자료형을 가변 자료형으로 혼동하지 마세요")
        void do_not_be_misled_into_thinking_that_immutable_types_are_mutable() {
            BigInteger fiveThousand = new BigInteger("5000");
            BigInteger fiftyThousand = new BigInteger("50000");
            BigInteger fiveHundredThousand = new BigInteger("500000");

            // BigInteger 클래스의 인스턴스는 불변입니다.
            BigInteger total = BigInteger.ZERO;
            total.add(fiveThousand);
            total.add(fiftyThousand);
            total.add(fiveHundredThousand);
            assertThat(total).isEqualTo(0);

            BigInteger total2 = BigInteger.ZERO;
            total2 = total2.add(fiveThousand);
            total2 = total2.add(fiftyThousand);
            total2 = total2.add(fiveHundredThousand);
            assertThat(total2).isEqualTo(555_000);
        }
    }

}
