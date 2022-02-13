package com.markruler.puzzler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("반복문 퍼즐")
class Puzzlers3LoopyTest {

    @Nested
    @DisplayName("Puzzle 24: 바이트의 즐거움")
    class Describe_Big_Delight_in_Every_Byte {

        @Test
        @DisplayName("서로 다른 타입을 비교하지 마세요")
        void avoid_mixed_type_comparisons() {
            assertThat(Byte.MIN_VALUE).isEqualTo((byte) -128);
            assertThat(Byte.MAX_VALUE).isEqualTo((byte) 127);

            // (byte) 0x90 == 0x90
            // Java는 이 표현식을 검사할 때 byte 타입을 int 타입으로 '기본 자료형 확장 변환'하고 int 타입끼리 비교합니다.
            // 그런데 byte 타입은 부호가 있기 때문에 부호 확장이 발생합니다.
            // 따라서 (byte) 0x90은 0x90에 해당하는 +144가 아니라 -112로 변환됩니다.
            assertThat(0x90).isEqualTo(0x90).isEqualTo(144);
            assertThat((byte) 0x90).isEqualTo((byte) -112);
            assertThat(0x90).isNotEqualTo((byte) 0x90);
        }
    }

    @Nested
    @DisplayName("Puzzle 25: 비정상적인 증가")
    class Describe_Inclement_Increment {

        @Test
        @DisplayName("한 표현식에서 같은 변수에 여러 번 값을 할당하지 마세요")
        void do_not_assign_to_the_same_variable_more_than_once_in_a_single_expression() {
            int j = 0;
            for (int i = 0; i < 100; i++) {
                // int tmp = j;
                // j = j + 1;
                // j = tmp;
                j = j++;
            }
            assertThat(j).isEqualTo(0);

            for (int i = 0; i < 100; i++) {
                // j = j + 1;
                j++;
            }
            assertThat(j).isEqualTo(100);
        }
    }

    @Nested
    @DisplayName("Puzzle 26: 반복문 안에서")
    class Describe_In_the_Loop {
        public static final int END = Integer.MAX_VALUE;
        public static final int START = END - 100;

        @Test
        @DisplayName("정수 타입을 사용할 때 바운더리 조건을 주의하세요")
        void integral_type_boundary_conditions() {
            int count1 = 0;
            // 모든 int 값은 항상 Integer.MAX_VALUE보다 작거나 같습니다.
            // for (int i = Integer.MAX_VALUE - 100; i <= Integer.MAX_VALUE; i++) {
            //     count1++;
            // }
            // assertThat(count1).isEqualTo(0);
            assertThat(Integer.MAX_VALUE + 1).isEqualTo(Integer.MIN_VALUE);

            // int 값이 거의 최댓값 바운더리까지 증가한다면 반복변수(loop index)를 long 타입으로 만드세요.
            for (long i = START; i <= END; i++) {
                count1++;
            }
            assertThat(count1).isEqualTo(101);

            int count2 = 0;
            int index = START;
            do {
                count2++;
            } while (index++ != END);
            assertThat(count2).isEqualTo(101);
        }
    }


    @Nested
    @DisplayName("Puzzle 27: 시프트 연산자와 변수 i")
    class Describe_Shifty_i {

        @Disabled("Infinite Loop")
        @Test
        @DisplayName("-1 << 32는 0이 아니라 -1이다")
        void shift_operator_32() {
            int index = 0;
            while (-1 << index != 0) {
                index++;
            }
            assertThat(index).isEqualTo(101);
        }

        @Test
        @DisplayName("shift 연산자는 오른쪽 피연산자(operand)의 하위 비트 5개만큼만 이동시킨다")
        void five_low_order_bits_of_their_right_operand_as_the_shift_distance() {
            assertThat(-1).isEqualTo(0xffffffff);
            assertThat(-1).isEqualTo(0B1111_1111_1111_1111_1111_1111_1111_1111);
            assertThat(-1 << 1).isEqualTo(0B1111_1111_1111_1111_1111_1111_1111_1110);
            assertThat(-1 << 10).isEqualTo(0B1111_1111_1111_1111_1111_1100_0000_0000);
            assertThat(-1 << 31).isEqualTo(0B1000_0000_0000_0000_0000_0000_0000_0000);

            // shift 연산자는 오른쪽 피연산자(operand)의 하위 비트 5개만큼만 이동시킨다.
            // 다시 말해서 2진수 00000(10진수 0개)부터 2진수 11111(10진수 31개)까지만 이동시킨다.
            // long일 경우 하위 비트 6개만큼 이동시킨다.
            assertThat(-1 << 32)
                    .isNotEqualTo(0B0000_0000_0000_0000_0000_0000_0000_0000)
                    .isEqualTo(0B1111_1111_1111_1111_1111_1111_1111_1111);
        }

        @Test
        @DisplayName("음수를 사용해도 하위 5비트만 사용한다")
        void negative_shift() {
            assertThat(-1 << -1)
                    .isEqualTo(-1 << 31)
                    .isEqualTo(0B1000_0000_0000_0000_0000_0000_0000_0000);
        }

        @Test
        @DisplayName("비트와 관련된 연산자는 되도록 상수를 사용한다")
        void shift_distances_should_if_possible_be_constants() {
            int distance = 0;
            for (int val = -1; val != 0; val <<= 1) {
                distance++;
            }
            assertThat(distance).isEqualTo(32);
        }
    }

    @Nested
    @DisplayName("Puzzle 28: 반복문")
    class Describe_Looper {
        // IEEE 754 부동소수점 연산
        final double i = Double.POSITIVE_INFINITY;

        @Test
        @DisplayName("무한은 무엇을 더해도 무한이다")
        void infinity() {
            assertThat(i + 1).isEqualTo(i);
        }
    }

    @Nested
    @DisplayName("Puzzle 29: 반복문의 신부")
    class Describe_Bride_of_Looper {
        // IEEE 754 부동소수점 연산
        final double i = Double.NaN;

        @Test
        @DisplayName("NaN(Not a Number)은 자신을 포함한 어떤 부동소수점 값과도 같지 않다")
        void not_a_number() {
            // NaN이 생성되었다는 것은 이미 계산에 문제가 있다는 뜻입니다.
            // 따라서 추가적으로 어떤 계산을 해도 복원할 수 없습니다.
            assertThat(i - i).isNotZero();
            assertThat(i).isNotEqualTo(i);
        }
    }

    @Nested
    @DisplayName("Puzzle 30: 반복문의 아들")
    class Describe_Son_of_Looper {
        // IEEE 754 부동소수점 연산
        final String str = "";
        final int integer = 1;

        @Test
        @DisplayName("i가 정수라면 0을 더해도 같지만 문자열이라면 다르다")
        void operator_overloading() {
            assertThat(str).isNotEqualTo(str + 0);
            assertThat(integer).isEqualTo(integer + 0);
            // 연산자 오버로딩은 혼란을 야기할 수 있습니다.
            // 이해하기 쉬운 변수, 메서드, 클래스 이름을 사용하면 주석처럼 프로그램의 가독성을 높일 수 있습니다.

            // 참고로 Java는 연산자 오버로딩을 문자열 연결 연산자에만 허용한다
            // https://stackoverflow.com/questions/77718
            // https://stackoverflow.com/questions/1686699
        }
    }

    @Nested
    @DisplayName("Puzzle 31: 반복문의 유령")
    class Describe_Ghost_of_Looper {
        short infinite = -1;

        @Disabled("Infinite Loop")
        @Test
        @DisplayName("short, byte, char 타입 변수에는 복합 할당 연산자를 사용하지 마세요")
        void do_not_use_compound_assignment_operators_on_short_byte_char() {
            // 이러한 타입에 복합 할당 연산자를 사용하면 혼합 자료형 연산이 일어납니다.
            // 게다가 연산될 때 자동으로 작은 타입으로 변환이 일어나므로 데이터가 손실될 수 있습니다.
            while (infinite != 0) {
                // >>> 연산자는 부호 없는 오른쪽 시프트 연산자입니다.
                // 다른 언어에는 없고 Java에만 있는 연산자입니다.
                // 오른쪽으로 비트를 이동할 때 비워지는 왼쪽 비트를 0으로 채웁니다.
                // 0101 >>> 0010, 1010 >>> 0101
                infinite >>>= 0;

                // 반면 >> 연산자는 부호가 있습니다.
                // 따라서 왼쪽에 추가되는 비트는 원래 왼쪽에 있던 비트와 같습니다.
                // 0101 >> 0010, 1010 >> 1101
            }
        }
    }

    @Nested
    @DisplayName("Puzzle 32: 반복문의 저주")
    class Describe_Curse_of_Looper {
        Integer i = new Integer(0);
        Integer j = new Integer(0);

        @Disabled("Infinite Loop")
        @Test
        @DisplayName("피연산자가 박스 타입일 때 대소 비교 연산자와 동등 비교 연산자는 서로 다른 방법으로 비교합니다")
        void boxed_numeric_types() {
            // http://java.sun.com/j2se/5.0/docs/guide/language/autoboxing.html
            // Java 5부터 오토박싱, 오토언박싱이 추가되었습니다.
            // 그래서 boxed numeric type도 비교 연산자를 사용할 수 있습니다.
            // 하지만 동등 비교 연산자(==, !=)는 값을 비교하는 것이 아닌 참조를 비교합니다.
            // 그래서 Java 9부터 생성자는 Deprecated 되었다.
            while (i <= j && j <= i && i != j) {
            }
        }
    }

    @Nested
    @DisplayName("Puzzle 33: 반복문의 악마")
    class Describe_Looper_Meets_the_Wolfman {
        int i = Integer.MIN_VALUE; // -2^31, 0x80000000
        // long l = Long.MIN_VALUE; // -2^63, 0x8000000000000000L

        @Disabled("Infinite Loop")
        @Test
        @DisplayName("Java는 정수 오버플로를 무시하기 때문에 오버플로를 조심하세요")
        void java_ignores_overflows_in_integer_computations() {
            // int 자료형의 Integer.MIN_VALUE는 16진수로 나타내면 0x80000000입니다.
            // 부호 비트가 1이고 나머지 비트는 모두 0입니다.
            // 이 숫자의 부호를 바꾸면 0x7fffffff+1이 됩니다.
            // 이 숫자는 다시 0x80000000이 됩니다.

            // 다르게 설명하면,
            // int 타입의 범위는 -2147483648 ~ 2147483647입니다.
            // 따라서 -2147483648은 부호가 바뀌었을 때 대응되는 숫자가 없습니다.
            // 그래서 부호를 바꿔도 -2147483648이 됩니다.
            while (i == -i && i != 0) {
            }
        }
    }

    @Nested
    @DisplayName("Puzzle 34: 큰 정수를 사용하는 반복")
    class Describe_Down_for_the_Count {
        int i = Integer.MIN_VALUE; // -2^31, 0x80000000
        long l = Long.MIN_VALUE; // -2^63, 0x8000000000000000L

        @Test
        @DisplayName("부동소수점 타입을 반복변수로 사용하지 마세요")
        void do_not_use_floating_point_loop_indices() {
            final int START = 2_000_000_000;
            int count = 0;
            // 2,000,000,000을 인수분해하면 2^10 * 5^9입니다.
            // 한마디로 2,000,000,000을 2진 표현으로 나타내면 10개의 0이 나옵니다.
            // 50을 2진 표현으로 나타내면 6개의 비트를 활용합니다.
            // 결과적으로 2,000,000,000에 50을 더하면 오른쪽 6개의 비트만 변경됩니다.
            // float 타입은 32비트지만, 정확도는 24비트입니다.
            // 따라서 이런 정수를 float 표현으로 나타내면 오른쪽 7개의 비트를 버리고 반올림하게 됩니다.
            // 2,000,000,000과 2,000,000,050에서 오른쪽 7개의 비트를 버리면 결과적으로 같은 숫자로 표현됩니다.
            assertThat((float) START)
                    .isNotSameAs((float) (START + 50))
                    .isEqualTo((float) (START + 50));

            for (float f = START; f < START + 50; f++) {
                count++;
            }
            assertThat(count).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Puzzle 35: 시계")
    class Describe_Minute_by_Minute {

        @Test
        @DisplayName("이 프로그램을 고치는 가장 쉬운 방법은 괄호를 사용해서 우선순위를 변경하는 것입니다")
        void insert_a_pair_of_parentheses() {
            int minutes = 0;
            for (int ms = 0; ms < 60 * 60 * 1000; ms++) {
                if (ms % 60 * 1000 == 0) { // ms % (60 * 1000)
                    minutes++;
                }
            }

            // 곱센 연산자(*)와 나머지 연산자(%)는 우선순위가 같습니다.
            assertThat(minutes)
                    .isNotEqualTo(60)
                    .isEqualTo(60000);
        }
        @Test
        @DisplayName("더 좋은 방법은 중요한 숫자를 적당한 이름의 상수로 만들어 사용하는 것입니다")
        void replace_all_magic_numbers_with_appropriately_named_constants() {
            int minutes = 0;
            final int MS_PER_MINUTE = 60 * 1000;
            final int MS_PER_HOUR = 60 * MS_PER_MINUTE;

            for (int ms = 0; ms < MS_PER_HOUR; ms++) {
                if (ms % MS_PER_MINUTE == 0) {
                    minutes++;
                }
            }

            assertThat(minutes)
                    .isEqualTo(60);
        }
    }

}
