package com.markruler.puzzler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("문자 퍼즐")
class Puzzlers2CharacterTest {

    @Nested
    @DisplayName("Puzzle 11: 마지막 웃음")
    class Describe_Th_Last_Laugh {

        @Test
        @DisplayName("char 타입을 더하면 각각의 값을 int 타입으로 바꾼다")
        void char_add_char() {
            assertThat("H" + "a").isEqualTo("Ha");
            assertThat('H' + 'a').isEqualTo(72 + 97);

            StringBuffer sb = new StringBuffer();
            sb.append('H');
            sb.append('a');
            assertThat(sb.toString()).isEqualTo("Ha");

            assertThat("" + 'H' + 'a').isEqualTo("Ha");
            assertThat("2 + 2 = " + 2 + 2).isEqualTo("2 + 2 = 22");
            assertThat(String.format("%c%c", 'H', 'a')).isEqualTo("Ha");
        }
    }

    @Nested
    @DisplayName("Puzzle 12: ABC")
    class Describe_ABC {
        // 배열을 포함한 모든 객체 참조는 문자열로 변환될 때 다음과 같이 처리된다.

        // 참조가 null이라면 문자열 "null"로 변환된다.
        // 반면에 null이 아니라면 참조 객체는 toString() 메서드를 호출해서 문자열로 변환된다.
        // 하지만 toString() 메서드의 리턴값이 null이라면 문자열 "null"로 변환된다.

        // 배열의 toString() 메서드는 Object 클래스에서 상속받은 것이다.
        // Object 클래스의 toString()은 "[클래스 이름@"과 "16진수로 이루어 객체 해시]"를 합친 문자열을 리턴한다.
        // null이 아닌 char 배열에 toString() 메서드를 호출하면
        // char 배열의 Class.getName() 메서드로 얻은 "[C"를 포함해서 "[C@16f0472]" 같은 문자열을 리턴한다.

        @Test
        @DisplayName("toString()")
        void char_array_toString() {
            String letters = "ABC";
            char[] numbers = {'1', '2', '3'};
            assertThat(letters + " easy as " + numbers).isNotEqualTo("ABC easy as 123");
            assertThat(letters + " easy as " + String.valueOf(numbers)).isEqualTo("ABC easy as 123");
        }
    }

    @Nested
    @DisplayName("Puzzle 13: 동물농장")
    class Describe_Animal_Farm {
        // String 타입 상수는 컴파일 시점에 인턴(intern)된다.
        // 한마디로 같은 문자열 상수는 같은 참조다.
        // 두 문자열을 같은 문자열 상수로 초기화했다면 같은 참조일 것이다.

        // Java 문서에 따르면 메서드를 호출해서 만든 문자열은 문자열 상수가 아니다.

        // [JLS-15.28]
        // https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.28
        // "Constant expressions of type String are always "interned" so as to share unique instances, using the method String.intern."
        // intern: 일정 구역에 억류[구속, 강제 수용]하다;

        String literal1 = "markruler";
        String literal2 = "markruler";
        String object = new String("markruler");
        String intern = object.intern();

        @Test
        @DisplayName("String intern")
        void interned_string() {
            // literal & literal
            assertThat(literal1).describedAs("==").isSameAs(literal2);
            assertThat(literal1).describedAs("equals()").isEqualTo(literal2);

            // literal & object
            assertThat(literal1).describedAs("!=").isNotSameAs(object);
            assertThat(literal1).describedAs("equals()").isEqualTo(object);

            // literal & intern
            // 문자열이 같아서 true인 것이 아니다.
            // 주소 공간이 같아서 true인 것이다.
            assertThat(literal1).describedAs("==").isSameAs(intern);
            assertThat(literal1).describedAs("equals()").isEqualTo(intern);
        }

        @Test
        @DisplayName("연산자 우선순위")
        void operator_precedence() {
            // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
            final String pig = "length: 10";
            final String dog = "length: " + pig.length();
            assertThat(pig).isEqualTo(dog);
            assertThat("Animals are equal: " + pig == dog).isFalse(); // +가 ==보다 우선순위가 높다
            assertThat("Animals are equal: " + (pig == dog)).isNotEqualTo("Animals are equal: true");
            assertThat("Animals are equal: " + pig.equals(dog)).isEqualTo("Animals are equal: true");
        }
    }

    @Nested
    @DisplayName("Puzzle 14: 이스케이프 문자")
    class Describe_Escape_Rout {

        @Test
        @DisplayName("문자열과 문자에는 유니코드 이스케이프 시퀀스 말고 일반 이스케이프 문자를 사용하라")
        void prefer_escape_sequences_to_Unicode_escapes_in_string_and_character_literals() {
            assertThat("a\u0022.length() + \u0022b".length())
                    .describedAs("총 글자 수").isNotEqualTo(26)
                    .describedAs("이스케이프 문자는 여러 글자여도 한 글자로 표현된다").isNotEqualTo(16)
                    .describedAs("Java는 문자열 안에 있는 유니코드도 코드로 본다").isEqualTo(2);

            assertThat("a\u0022.length() + \u0022b".length()).isEqualTo("a".length() + "b".length());

            assertThat("a\".length() + \"b".length()).isEqualTo(16);
        }
    }

    @Nested
    @DisplayName("Puzzle 15-17: \"유니코드 이스케이프 시퀀스\"는 정말 필요한 곳이 아니라면 사용하지 마세요")
    class Describe_Hello_Whirled {
        /**
         * 유니코드 이스케이프 시퀀스는 주석 안에 있더라도 해석된다.
         *
         * <p>
         * Generated by the IBM IDL-to-Java compiler, version 1.0<br>
         * from F:\TestRoot\apps\a1/units\include\PolicyHome.idl -> 역슬래시 u 뒤에 nits는 16진수 문자가 아니다.<br>
         * Wednesday, June 17, 1998 6:44:40 o’clock AM GMT+00:00<br>
         */
        String test1 = "";

        /**
         * API 문서 주석에는 유니코드 이스케이프 시퀀스 대신 HTML 이스케이프를 사용하세요.
         * <p>
         * Ah\u00e9
         * Ah&eacute;
         */
        String test2 = null;

        @Test
        @DisplayName("유니코드 이스케이프 시퀀스는 프로그램을 알아보기 힘들게 만듭니다")
        void unicode_escapes_reduce_program_clarity() {
            // 무엇을 할 수 있다고 해서 그것을 꼭 해야 하는 것은 아닙니다.
            // 또한 그것이 문제를 일으킬 수 있다면 절대로 하지 마세요!
            // 물론 유니코드 이스케이프 시퀀스는 프로그램에서 표현할 수 없는 문자를 삽입할 때 필요합니다.
            // 하지만 이 이외의 경우에는 절대로 사용하지 마세요.
            assertThat("\u0048\u0065\u006C\u006C\u006F\u0020\u0057\u006F\u0072\u006C\u0064")
                    .isEqualTo("Hello World");
        }
    }

    @Nested
    @DisplayName("Puzzle 18: 스트링 치즈")
    class Describe_String_Cheese {

        // 캐릭터 셋(charset)은 '문자와 문자 인코딩 스키마의 조합'을 말합니다.
        // 다시 말해서 문자와 문자를 표현하기 위한 코드 변환 방법입니다.
        // 변환 방법은 캐릭터 셋마다 완전히 다릅니다.
        @Test
        @DisplayName("byte 배열을 문자열로 변환할 때는 반드시 캐릭터 셋을 사용해야 한다")
        void characterset() {

            byte[] bytes = new byte[256];
            for (int i = 0; i < 256; i++) {
                bytes[i] = (byte) i;
            }

            assertThat(java.nio.charset.Charset.defaultCharset()).isEqualTo(StandardCharsets.UTF_8);

            // String utf8str = new String(bytes, StandardCharsets.UTF_8);
            String utf8str = new String(bytes);
            assertThat((int) utf8str.charAt(255)).isEqualTo(65533);

            String iso8859_1str = new String(bytes, StandardCharsets.ISO_8859_1);
            assertThat((int) iso8859_1str.charAt(255)).isEqualTo(255);
        }
    }

    @Nested
    @DisplayName("Puzzle 19: 분류")
    class Describe_Classy_Fire {
        /*
            // 여러 줄의 코드를 주석 처리하는 가장 좋은 방법은 한 줄 주석을 여러 번 사용하는 것입니다.
            // 여러 줄 주석은 주석이 제대로 처리되지 않는 경우가 있습니다.
            // 여러 줄 주석보다는 한 줄 주석을 사용하기 바랍니다.
         */
    }

    @Nested
    @DisplayName("Puzzle 20: 내 클래스 이름(1)")
    class Describe_Whats_My_Class {

        @Test
        @DisplayName("replaceAll()의 첫 번째 매개변수는 문자열이 아니라 정규표현식이다")
        void sut_String_replaceAll_takes_a_regular_expression_as_its_first_parameter() {
            assertThat(this.getClass().getName()).isEqualTo("com.markruler.puzzler.Puzzlers2CharacterTest$Describe_Whats_My_Class");
            assertThat(this.getClass().getName().replaceAll(".", "/"))
                    .describedAs("replaceAll()의 첫 번째 매개변수는 문자열이 아니라 정규표현식이다")
                    .isNotEqualTo("com/markruler/puzzler/Puzzlers2CharacterTest$Describe_Whats_My_Class")
                    .isEqualTo("////////////////////////////////////////////////////////////////////");

            assertThat(this.getClass().getName().replaceAll("\\.", "/"))
                    .isEqualTo("com/markruler/puzzler/Puzzlers2CharacterTest$Describe_Whats_My_Class");

            assertThat(this.getClass().getName().replaceAll(Pattern.quote("."), "/"))
                    .isEqualTo("com/markruler/puzzler/Puzzlers2CharacterTest$Describe_Whats_My_Class");
        }
    }

    @Nested
    @DisplayName("Puzzle 21: 내 클래스 이름(2)")
    class Describe_Whats_My_Class_Take2 {

        @Test
        @DisplayName("replaceAll()의 첫 번째 매개변수는 문자열이 아니라 정규표현식이다")
        void sut_String_replaceAll_takes_a_regular_expression_as_its_first_parameter() {
            assertThat(this.getClass().getName()).isEqualTo("com.markruler.puzzler.Puzzlers2CharacterTest$Describe_Whats_My_Class_Take2");

            assertThat(this.getClass().getName().replaceAll("\\.", File.separator))
                    .describedAs("Windows에서는 separator가 backslash이기 때문에 예외가 발생할 수 있다")
                    .isEqualTo("com/markruler/puzzler/Puzzlers2CharacterTest$Describe_Whats_My_Class_Take2");

            assertThat(this.getClass().getName().replaceAll(Pattern.quote("."), Matcher.quoteReplacement(File.separator)))
                    .isEqualTo("com/markruler/puzzler/Puzzlers2CharacterTest$Describe_Whats_My_Class_Take2");

            // String.replace()는 String.replaceAll()과 같은 일을 하지만
            // 매개변수를 모두 일반 문자열로 다룹니다.
            assertThat(this.getClass().getName().replace(".", File.separator))
                    .isEqualTo("com/markruler/puzzler/Puzzlers2CharacterTest$Describe_Whats_My_Class_Take2");
        }
    }

    @Nested
    @DisplayName("Puzzle 22: URL 눈속임")
    class Describe_Dupe_of_URL {

        @Test
        @DisplayName("Java에는 goto 문이 없으므로 Label을 사용할 필요가 거의 없습니다")
        void label() {
            http:
//www.google.com;
            System.out.println("위에서 http:는 label로 인식되고 // 다음 문장은 주석으로 인식됩니다");
        }
    }

    @Nested
    @DisplayName("Puzzle 23: No Pain, No Gain")
    class Describe_No_Pain_No_Gain {

        private final Random random = new Random();
        private final String pain = "Pain";
        private final String gain = "Gain";
        private final String main = "Main";

        @Test
        @DisplayName("최대한 익숙한 형태로 코드를 작성하세요")
        void use_familiar_idioms_and_APIs_whenever_possible() {
            StringBuffer word = null;
            switch (random.nextInt(3)) { // 0~2
                case 1:
                    // 익숙하지 않은 API를 사용할 때는 API 문서를 주의 깊게 확인하세요
                    word = new StringBuffer('P');
                    // https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javac.html
                    break; // javac -Xlint:fallthrough
                case 2:
                    word = new StringBuffer('G');
                    break;
                default:
                    word = new StringBuffer('M');
                    break;
            }
            word.append('a');
            word.append('i');
            word.append('n');
            assertThat(word).hasToString("ain");

            assertThat("PGM".charAt(random.nextInt(3)) + "ain").isIn(pain, gain, main);

            // 이 방법을 사용하자
            final String[] candidates = {pain, gain, main};
            assertThat(randomElement(candidates)).isIn(pain, gain, main);
        }

        private String randomElement(String[] strings) {
            return strings[random.nextInt(strings.length)];
        }
    }
}
