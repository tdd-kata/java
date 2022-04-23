package org.xpdojo.designpatterns._02_structural_patterns._06_flyweight;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntegerWrapperTest {

    /**
     * {@link Integer#valueOf(int)}의 JavaDoc을 보면 기본적으로 -128부터 127까지 캐싱한다.
     * 이외의 숫자도 자주 사용하면 캐싱한다.
     *
     * @see Integer#valueOf(int)
     */
    @Test
    void sut_integer_wrapper_cache() {
        Integer integer1 = Integer.valueOf("1");
        Integer integer2 = Integer.valueOf("1");

        assertThat(integer1)
                .isSameAs(integer2)
                .isEqualTo(integer2);
    }

    @Test
    void sut_integer_wrapper_not_cache() {
        Integer integer1 = Integer.valueOf("128");
        Integer integer2 = Integer.valueOf("128");

        assertThat(integer1)
                .isNotSameAs(integer2) // 같은 Integer 객체가 아니다.
                .isEqualTo(integer2);
    }
}
