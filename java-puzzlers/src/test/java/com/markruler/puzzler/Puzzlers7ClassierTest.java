package com.markruler.puzzler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("클래스 심화 퍼즐")
class Puzzlers7ClassierTest {

    @Nested
    @DisplayName("Puzzle 66: private 접근 제한자 문제")
    class Describe_Private_Matter {

        class Base {
            public String className = "Base";
        }

        class Derived extends Base {
            private String className = "Derived";
        }

        @Test
        @DisplayName("하이딩을 사용하지 마세요")
        void avoid_hiding() {
            Derived derived = new Derived();
            assertThat(derived.className).isEqualTo("Derived");
            // className 필드가 메서드였다면 Derived.className()이 Base.className()을 오버라이딩했을 것입니다.
            // 하지만 필드이므로 오버라이딩과 관련 없이 하이딩(Hiding)을 수행합니다.

            // 하이딩은 필드, 정적 메서드, 또는 중첩 타입(nested type)의 이름이
            // 부모의 접근 가능한 필드, 메서드, 타입의 이름과 동일할 경우에 발생합니다.
            // 이런 경우를 만들지 마세요.
        }
    }

}
