package string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("표현식 퍼즐")
class Puzzlers1ExpressiveTest {

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
        void binary_floating_point() {
            // Java는 1.1을 정확하게 double로 표현할 수 없다.
            assertThat(2.00 - 1.10).isEqualTo(0.8999999999999999);

            // BigDecimal(double) 생성자는 사용하지 말자.
            assertThat(new BigDecimal(2.00).subtract(new BigDecimal(1.10)))
                    .isEqualTo(new BigDecimal(0.89999999999999991118215802998747676));
            assertThat(new BigDecimal("2.00").subtract(new BigDecimal("1.10")))
                    .isEqualTo(new BigDecimal("0.90"));
        }
    }

    @Nested
    @DisplayName("Puzzle 3: Long 자료형 나눗셈")
    class Describe_LongDivision {
        /*
            "When working with large numbers, watch out for overflow — it’s a silent killer"
         */

        @Nested
        @DisplayName("target type이 long이어도 int 타입끼리 곱해서 int 크기보다 커지면")
        class Context_with_int {
            final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
            final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000; // integer multiplication implicitly cast to long

            @Test
            @DisplayName("overflow가 발생한다")
            void it_should_overflow() {
                /*
                    하루는 86,400,000 밀리초다.
                 */
                assertThat(MILLIS_PER_DAY).isEqualTo(86_400_000L);

                /*
                   하루는 86,400,000,000 마이크로초일 것이라고 기대하지만
                   실제 값은 500,654,080으로 계산된다.

                   'long = int * int'
                   문제는 int 자료형끼리 곱셈 연산이 이루어지고 overflow가 발생한 것이다.
                   그 후 long 자료형으로 '기본 자료형 확장 변환 widening primitive conversion'이 되었다.

                   Java는 Target typing을 지원하지 않기 때문에 결과 자료형이 long이어도
                   기존 자료형이 long 연산을 하지 않는다.
                 */
                assertThat(MICROS_PER_DAY)
                        .isNotEqualTo(86_400_000_000L)
                        .isEqualTo(500_654_080L);

                assertThat(MICROS_PER_DAY / MILLIS_PER_DAY)
                        .isNotEqualTo(1_000)
                        .isEqualTo(5);
            }
        }

        @Nested
        @DisplayName("곱셈에 사용되는 첫 번째 숫자를 long 자료형으로 만들어 주면")
        class Context_with_long {
            final long LONG_MILLIS_PER_DAY = 24L * 60 * 60 * 1000;
            final long LONG_MICROS_PER_DAY = 24L * 60 * 60 * 1000 * 1000;

            @Test
            @DisplayName("long 크기 내에서 overflow가 발생하지 않는다")
            void it_should_not_overflow() {
                assertThat(LONG_MICROS_PER_DAY).isEqualTo(86_400_000_000L);
                assertThat(LONG_MICROS_PER_DAY / LONG_MILLIS_PER_DAY).isEqualTo(1000L);
            }
        }
    }

    @Nested
    @DisplayName("Puzzle 4: 초등학교 수준의 문제")
    class ItsElementaryTest {
        /*
            "Always use a capital el (L) in long literals, never a lowercase el (l)"
         */
        @Test
        @DisplayName("Long 자료형을 입력할 때 소문자 l이 아닌 대문자 L을 사용한다")
        void use_capital_L() {
            // 알파벳 l과 숫자 1은 쉽게 헷갈린다.
            assertThat(12345 + 5432l)
                    .isNotEqualTo(66666L)
                    .isEqualTo(17777L);

            assertThat(12345 + 54321L)
                    .isEqualTo(66666L);
        }
    }

    @Nested
    @DisplayName("Puzzle 5: 16진수의 즐거움")
    class TheJoyOfHexTest {

        /*
            "To avoid this sort of difficulty, it is generally best to avoid mixed-type computations."
            다양한 진법의 숫자를 함께 연산하지 마세요.
         */

        final long validLong = 0x100_000_000L;
        final int intCafebebe = 0xcafebebe; // 4 bytes (32 bits)
        final long longCafebebe = 0xcafebebeL; // 8 bytes (64 bits)

        @Test
        @DisplayName("int 타입이 32비트를 넘을 경우 상위 비트 값 때문에 음수가 될 수 있다")
        void negative() {
            /*
                Java에서 10진수는 항상 양의 정수다.
                음수로 만들고 싶다면 단항 부정 연산자(-)를 사용한다.
                하지만 8진수나 16진수는 상위 비트(high-order bit)가 정의되면 음수로 인식한다.
                따라서 0xcafebebe는 10진수로 표현할 경우 음수가 된다.
             */
            assertThat(-889_274_690).isEqualTo(intCafebebe); // 33번째 bit 손실
            assertThat(Long.toHexString(validLong + intCafebebe))
                    .isNotEqualTo("1cafebebe")
                    .isEqualTo("cafebebe");

            assertThat(0xffffffffcafebebeL).isEqualTo(intCafebebe); // 상위 32비트 -1
            assertThat(0xffffffff).isEqualTo(-1); // 상위 32비트 -1
            assertThat(0x0000000100000000L).isEqualTo(validLong); // 상위 32비트 1
            assertThat(0x00000001).isEqualTo(1); // 상위 32비트 1
            assertThat(0xffffffffcafebebeL + 0x0000000100000000L).isEqualTo(0xcafebebeL);
        }

        @Test
        @DisplayName("long 타입으로 선언해서 부호 확장으로 값이 바뀌는 것을 막는다")
        void positive() {
            assertThat(3_405_692_606L).isEqualTo(longCafebebe);
            assertThat(Long.toHexString(validLong + longCafebebe))
                    .isNotEqualTo("cafebebe")
                    .isEqualTo("1cafebebe");

            assertThat(0x00000000cafebebeL)
                    .describedAs("상위 32비트 0")
                    .isEqualTo(longCafebebe);
            assertThat(0x0000000100000000L)
                    .describedAs("상위 32비트 1")
                    .isEqualTo(validLong);
            assertThat(0x00000001)
                    .describedAs("상위 32비트 1")
                    .isEqualTo(1);
            assertThat(0x00000000cafebebeL + 0x0000000100000000L)
                    .describedAs("상위 32비트 1과 cafebebe를 더한다")
                    .isEqualTo(0x1cafebebeL);
        }
    }

    @Nested
    @DisplayName("Puzzle 6: 다중 자료형 변환")
    class Describe_Multicast {

        @Test
        @DisplayName("부호 확장 때문에 발생하는 값")
        void sign_extension() {
            assertThat((int) (char) (byte) -1).isEqualTo(65535);

            assertThat(-1).isEqualTo((byte) -1); // int(32 bit) -> byte(8 bit)
            assertThat(0B1111_1111_1111_1111_1111_1111_1111_1111).isEqualTo(-1); // int -1은 2진수로 1이 32개
            assertThat((byte) 0B1111_1111).isEqualTo((byte) -1); // byte -1은 2진수로 1이 8개

            /*
                byte(8 bit) -> char(16 bit)
                일반적으로 작은 타입에서 큰 타입으로 변환할 때는 부호도 문제없이 변환되지만,
                char 타입처럼 음수가 없는 타입은 변환이 제대로 되지 않는다.
                byte 타입이 char 타입으로 변환될 때는 '기본 자료형 확장 변환'이 아니라
                '기본 자료형 변환 후 축소 변환'이 된다.
                byte 타입이 int 타입으로 확장 변환된 후 char 타입으로 축소 변환된다.
             */
            assertThat(0B1111_1111_1111_1111)
                    .isEqualTo(65535)
                    .isEqualTo((char) (int) (byte) -1)
                    .isEqualTo((char) (byte) -1);
        }
    }

    @Nested
    @DisplayName("Puzzle 7: 변수 교환")
    class Describe_Swap_Meat {

        @Test
        @DisplayName("하나의 표현식에 동일한 변수를 여러 번 할당하지 않는다")
        void do_not_assign_to_the_same_variable_more_than_once_in_a_single_expression() {
            int x = 1984;
            int y = 2001;
            x ^= y ^= x ^= y;

            assertThat(x).isEqualTo(0);
            assertThat(y).isEqualTo(1984);
        }

        @Test
        @DisplayName("Java에서 x ^= expr")
        void in_java() {
            /*
                Java는 피연산자를 왼쪽에서 오른쪽으로 계산한다.
                x ^= expr 형태의 표현식을 계산할 때, x는 expr이 계산되기 전에 추출되고 expr이 계산된 후에 합쳐진다.

                반면 대부분의 C와 C++ 컴파일러는 x ^= expr에서 expr을 먼저 계산하고 x의 값을 추출한다.
             */
            int x = 1984;
            int y = 2001;
            int tmp1 = x; // x 획득
            int tmp2 = y; // y 획득
            int tmp3 = x ^ y; // x ^ y 계산
            assertThat(x).isEqualTo(1984);
            assertThat(y).isEqualTo(2001);
            assertThat(tmp1).isEqualTo(1984);
            assertThat(tmp2).isEqualTo(2001);
            assertThat(tmp3).isEqualTo(17);
            x = tmp3; // x ^ y를 x에 저장
            assertThat(x).isEqualTo(17);
            y = tmp2 ^ tmp3; // 원래 x를 y에 저장
            assertThat(y).isEqualTo(1984);
            x = tmp1 ^ y; // x에 0을 저장
            assertThat(x).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Puzzle 8: XX")
    class Describe_Dos_Equis {

        @Test
        @DisplayName("조건 연산자를 사용할 때는 두 번째와 세 번째 피연산자의 타입을 일치시키고 사용한다")
        void use_the_same_type_for_the_second_and_third_operands_in_conditional_expressions() {
            char x = 'X';
            int i = 0;

            assertThat(true ? x : 0).describedAs("단일 자료형 연산").isEqualTo('X');
            assertThat(false ? i : x).describedAs("혼합 자료형 연산").isEqualTo(88);
        }
    }

    @Nested
    @DisplayName("Puzzle 9: 같은 것 같으면서도 다른 것 (1)")
    class Describe_Tweedledum {

        /*
            byte, short, char 타입의 변수에 복합 할당 연산자를 사용하지 않는다.
            int 타입의 변수에 복합 할당 연산자를 적용할 때도 우측에 long, float, double 타입의 표현식이 오지 않게 한다.
         */

        @Test
        @DisplayName("복합 할당 연산자(op=)는 위험하다")
        void compound_assignment_expressions_can_be_dangerous() {
            short x = 0;
            int i = 123456;

            /*
                E1과 E2는 피연산자, op는 연산자다.
                op=는 복합 할당 연산자(+=, -=, ...)를 의미한다.

                Java는 T가 E1의 자료형일 때
                복합 할당 E1 op= E2가
                단순 할당 E1 = (T)((E1) op (E2))와 같다고 정의한다.
                즉, 복합 할당 연산자는 연산 결과를 왼쪽 변수의 자료형으로 자동 변환한다.
                만약 연산 결과가 왼쪽 변수의 자료형보다 커지면 기본 자료형 축소 변환이 일어난다.
                반면 단순 할당하면 컴파일 시점에 오류가 발생한다.
                x = x + i; // 'possible loss of precision'
             */
            x += i;
            assertThat(x).isEqualTo((short) -7616);
        }
    }

    @Nested
    @DisplayName("Puzzle 10: 같은 것 같으면서도 다른 것 (2)")
    class Describe_Tweedledee {

        @Test
        @DisplayName("복합 할당 연산자의 왼쪽 피연산자가 String 타입이라면 오른쪽 피연산자로 모든 자료형이 올 수 있다")
        void left_hand_side_is_of_type_String() {
            Object x = "Buy";
            String i = "Effective Java!";

            // x = x + i;
            x += i;
            assertThat(x).isEqualTo("BuyEffective Java!");
        }
    }
}
