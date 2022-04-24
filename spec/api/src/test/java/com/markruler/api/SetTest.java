package com.markruler.api;

import com.google.common.collect.ImmutableSortedSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @see java.util.concurrent.ConcurrentSkipListSet
 * @see java.util.concurrent.CopyOnWriteArraySet
 * @see java.util.concurrent.CopyOnWriteArrayList
 * @see java.util.concurrent.ConcurrentHashMap
 * @see java.util.concurrent.ConcurrentLinkedDeque
 * @see java.util.concurrent.ConcurrentSkipListMap
 */
@DisplayName("Set")
class SetTest {

    /**
     * 객체를 중복해서 저장할 수 없고 하나의 null 값만 저장할 수 있다.
     * 저장 순서가 유지되지 않는다.
     * 만약 요소의 저장 순서를 유지해야 한다면 JDK 1.4부터 제공하는 <code>LinkedHashSet</code> 클래스를 사용한다.
     * thread-safe를 위해서는 synchronized를 사용해야 한다.
     *
     * @see java.util.Collections#synchronizedSet(Set)
     * @see java.util.LinkedHashSet
     */
    @Nested
    @DisplayName("HashSet")
    class Describe_HashSet {

        @ParameterizedTest(name = "HashSet({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        void sut_HashSet(String first, String second, String third) {
            Set<String> hashSet = new HashSet<>();
            hashSet.add(first);
            hashSet.add(second);
            hashSet.add(third);

            assertThat(hashSet)
                    .hasSize(3)
                    .containsExactly(first, second, third)
                    .doesNotContain("invalid");

            hashSet.add(first);
            assertThat(hashSet)
                    .hasSize(3)
                    .containsExactly(first, second, third);
        }
    }

    @Nested
    @DisplayName("SortedSet")
    class Describe_SortedSet {

        /**
         * @see java.util.SortedSet
         * @see com.google.common.collect.ImmutableSortedSet
         */
        @ParameterizedTest(name = "ImmutableSortedSet({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 5, 9",
        })
        @DisplayName("ImmutableSortedSet")
        void sut_SortedSet_descending(String first, String second, String third) {
            Set<String> sortedSet = ImmutableSortedSet.of(first, second, third).descendingSet();

            assertThat(sortedSet)
                    .hasSize(3)
                    .containsExactly(third, second, first);

            assertThatThrownBy(() -> sortedSet.add(first))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    /**
     * TreeSet은 이진탐색트리(Binary Search Tree) 중에서도
     * 성능을 향상시킨 레드-블랙 트리(Red-Black Tree)로 구현되어 있다.
     * 기본적으로 nature ordering을 사용한다.
     *
     * @see <a href="https://coding-factory.tistory.com/555">TreeSet 예제</a>
     */
    @Nested
    @DisplayName("TreeSet")
    static class Describe_TreeSet {

        @ParameterizedTest(name = "TreeSet({argumentsWithNames})")
        @MethodSource("generateData")
        void sut_TreeSet(List<Integer> data) {
            TreeSet<Integer> treeSet = new TreeSet<>(data);

            treeSet.add(data.get(0));
            assertThat(treeSet).hasSize(data.size());

            assertThat(treeSet.first()).isEqualTo(data.get(0)); // min
            assertThat(treeSet.last()).isEqualTo(data.get(data.size() - 1)); // max

            assertThat(treeSet.higher(33)).isEqualTo(40); // greater Than
            assertThat(treeSet.lower(33)).isEqualTo(30); // less Than
            assertThat(treeSet.ceiling(45)).isEqualTo(50); // greater Than Or Equal To
            assertThat(treeSet.floor(45)).isEqualTo(40); // less Than Or Equal To

            assertThat(treeSet.headSet(45)).containsExactly(10, 20, 30, 40); // less Than
            assertThat(treeSet.tailSet(51)).containsExactly(60, 70, 80, 90, 100); // greater Than
        }

        static Stream<Arguments> generateData() {
            return Stream.of(
                    Arguments.of(Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100))
            );
        }
    }

    @Nested
    @DisplayName("EnumSet")
    static class Describe_EnumSet {

        enum Color {
            RED, GREEN, BLUE
        }

        @Test
        void sut_EnumSet() {
            Set<Color> enumSet = EnumSet.of(Color.RED, Color.GREEN);

            assertThat(enumSet)
                    .hasSize(2)
                    .containsExactly(Color.RED, Color.GREEN);

            enumSet.add(Color.BLUE);

            assertThat(enumSet)
                    .hasSize(3)
                    .containsExactly(Color.RED, Color.GREEN, Color.BLUE);
        }

        /**
         * [Effective Java - ITEM 36] 비트 필드 대신 <code>EnumSet</code>을 사용해라.
         * <code>EnumSet</code>의 유일한 단점은 불변하게 만들 수 없다는 것이다.
         * <code>Collections.unmodifiableSet</code>은 명확성과 성능면에서 희생을 해야한다.
         */
        @Test
        void sut_unmodifiableSet_EnumSet() {
            Set<Color> enumSet = Collections.unmodifiableSet(EnumSet.allOf(Color.class));

            assertThat(enumSet)
                    .hasSize(3)
                    .containsExactly(Color.RED, Color.GREEN, Color.BLUE);

            assertThatThrownBy(() -> enumSet.add(Color.RED))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
