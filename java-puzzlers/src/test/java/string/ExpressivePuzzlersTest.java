package string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("표현식 퍼즐")
class ExpressivePuzzlersTest {

    @Nested
    @DisplayName("Puzzle 1: 홀수 확인")
    class OddityTest {

        private boolean isOdd(final int number) {
            // return number % 2 == 1; // 음수일 경우 -1이기 때문에 틀림
            // return number % 2 != 0;
            return (number & 1) != 0; // 성능이 굉장히 중요한 경우 비트 연산
        }

        @Test
        @DisplayName("양수 홀수 검사")
        void positive() {
            assertThat(isOdd(1)).isTrue();
            assertThat(isOdd(2)).isFalse();
            assertThat(isOdd(13)).isTrue();
        }

        @Test
        @DisplayName("0 홀수 검사")
        void zero() {
            assertThat(isOdd(0)).isFalse();
        }

        @Test
        @DisplayName("음수 홀수 검사")
        void negative() {
            assertThat(isOdd(-1)).isTrue();
            assertThat(isOdd(-2)).isFalse();
        }
    }

    @Nested
    @DisplayName("Puzzle 2: 변화를 위한 시간")
    class TimeForAChangeTest {

        /*
            정확한 결과가 필요하다면 절대로 float이나 double 자료형을 사용하지 마세요.
            금융 계산 같은 곳에서는 반드시 int, long, BigDecimal 자료형을 사용하기 바랍니다.
         */
        @Test
        @DisplayName("이진 부동소수점 연산")
        void floating() {
            // Java는 1.1을 정확하게 double로 표현할 수 없다.
            assertThat(2.00 - 1.10).isEqualTo(0.8999999999999999);

            // BigDecimal(double) 생성자는 사용하지 말자.
            assertThat(new BigDecimal(2.00).subtract(new BigDecimal(1.10)))
                    .isEqualTo(new BigDecimal(0.89999999999999991118215802998747676));
            assertThat(new BigDecimal("2.00").subtract(new BigDecimal("1.10")))
                    .isEqualTo(new BigDecimal("0.90"));
        }
    }
}
