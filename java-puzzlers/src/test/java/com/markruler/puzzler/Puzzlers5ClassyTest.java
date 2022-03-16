package com.markruler.puzzler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("클래스 퍼즐")
class Puzzlers5ClassyTest {

    @Nested
    @DisplayName("Puzzle 46: 애매한 생성자")
    class Describe_Case_of_the_Confusing_Constructor {

        public class Confusing {

            String name;

            private Confusing(Object o) {
                this.name = "Object";
            }

            private Confusing(double[] dArray) {
                this.name = "double";
            }
        }

        @Test
        @DisplayName("메서드를 오버로딩하면 코드가 복잡해집니다")
        void overload_resolution_can_be_confusing() {
            // 자바는 오버로딩 메서드를 호출할 때 2가지 과정을 거칩니다.
            // 일단 첫 번째는 사용할 수 있는 메서드를 고르는 일입니다.
            // 두 번째는 첫 번째 과정에서 선택한 메서드 중에서
            // 사용할 수 있는 가장 구체적인 형태의 메서드를 선택하는 것입니다.
            // 여기서 구체적이라는 말은 매개변수로 사용될 수 있는 가장 적은 형태의 자료형을 사용한다는 것을 의미합니다.

            // 첫 번째 과정에서는 '실제 매개변수가 무엇인가?'라는 정보는 활용하지 않습니다.
            // 실제 매개변수는 어떤 메서드가 더 구체적인지 확인하는 두 번째 과정에서 사용됩니다.
            Confusing confusing = new Confusing(null);
            assertThat(confusing.name).isEqualTo("double");

            // 컴파일러가 특정한 형태의 오버로딩 메서드를 호출하게 만들고 싶다면
            // 실제 매개변수로 전달하는 자료형을 원하는 자료형으로 강제 변환해 주세요.
            // 하지만 이렇게 오버로딩하는 것은 조금 이상합니다.
            // 일반적으로 API를 설계한다면 사용자가 이런 방식으로 자료형 변환을 하지 않아도 사용할 수 있게 설계하세요.
            // 기능이 다른 메서드라면 오버로딩하지 말고 다른 이름을 사용하기 바랍니다.
        }
    }

}
