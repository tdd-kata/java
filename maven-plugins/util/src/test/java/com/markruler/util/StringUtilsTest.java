package com.markruler.util;

import com.markruler.error.StringConcatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("StringUtils 클래스")
class StringUtilsTest {

    @Nested
    @DisplayName("concatenate 메서드는")
    class Describe_concatenate {

        @Test
        @DisplayName("문자열이 하나일 경우 예외를 던진다")
        void should_throw_string_concat_exception() {
            assertThatThrownBy(() -> StringUtils.concatenate(""))
                    .isInstanceOf(StringConcatException.class);;
        }

        @Test
        @DisplayName("문자열이 두 개 이상일 경우 문자열을 연결해서 리턴한다")
        void should_concatenate_strings() {
            assertThat(StringUtils.concatenate("a", "b"))
                    .isEqualTo("ab");
        }
    }

}
