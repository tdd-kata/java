package com.markruler.puzzler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("예외 처리 퍼즐")
class Puzzlers4ExceptionalTest {

    @Nested
    @DisplayName("Puzzle 36: 결정하기 힘든 프로그램")
    class Describe_Indecision {

        private boolean decision() {
            try {
                return true;
            } finally {
                return false;
            }
        }

        @Test
        @DisplayName("프로그램은 true를 리턴하려고 시도(try)하지만 최종적(finally)으로 false를 리턴한다")
        void program_tries_to_return_true_but_finally_it_returns_false() {
            assertThat(decision()).isFalse();
        }
    }

    @Nested
    @DisplayName("Puzzle 37: 이상한 예외")
    static class Describe_Exceptionally_Arcane {

        // try 구문에서 catch 구문에 적혀 있는 예외를 발생시키지 않는다면 컴파일 에러
        // catch 구문에서 Exception 또는 Throwable을 처리할 때는 try 구문의 내용에 상관없이 정상적으로 컴파일

        interface Type1 {
            boolean f() throws CloneNotSupportedException;
        }

        interface Type2 {
            boolean f() throws InterruptedException;
        }

        interface Type3 extends Type1, Type2 {}

        class Arcane implements Type3 {
            public boolean f() {
                return true;
            }
        }

        @Test
        @DisplayName("인터페이스 메서드에서 throws 키워드를 사용하는 것은 예외를 제한하는 기능입니다")
        void limits_the_set_of_checked_exceptions() {
            Type3 t3 = new Arcane();
            assertThat(t3.f()).isTrue();
        }
    }

}
