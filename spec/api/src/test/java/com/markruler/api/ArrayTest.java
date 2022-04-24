package com.markruler.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

@DisplayName("Array")
class ArrayTest {

    @Nested
    @DisplayName("초기화")
    class Describe_Array {

        @Test
        @DisplayName("1차원 배열")
        void sut_init_one_dimensional_array() {
            int[] array1 = {1, 2, 3};
            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);
            int[] array2 = new int[]{4, 5, 6,};
            assertThat(array2)
                    .hasSize(3)
                    .containsExactly(4, 5, 6);

            int[] array3 = new int[]{4, 5, 6, 7};
            assertThat(array3)
                    .hasSize(4)
                    .containsExactly(4, 5, 6, 7);

            int[] array4 = new int[3];
            assertThat(array4)
                    .hasSize(3)
                    .containsExactly(0, 0, 0);

            int[] array5 = {};
            assertThat(array5)
                    .isEmpty();

        }

        @Test
        @DisplayName("2차원 배열")
        void sut_init_two_dimensional_array() {
            int[][] array1 = {{1, 2, 3}, {4, 5, 6}};
            int expectedFirstDimension1 = 2;
            int expectedSecondDimension1 = 3;

            assertThat(array1)
                    .hasDimensions(expectedFirstDimension1, expectedSecondDimension1)
                    .contains(new int[]{1, 2, 3}, atIndex(0))
                    .contains(new int[]{4, 5, 6}, atIndex(1))
                    .isDeepEqualTo(new int[][]{{1, 2, 3}, {4, 5, 6}});

            int[][] array2 = new int[][]{{1, 2, 3}, {4, 5, 6}};
            int expectedFirstDimension2 = 2;
            int expectedSecondDimension2 = 3;

            assertThat(array2)
                    .hasDimensions(expectedFirstDimension2, expectedSecondDimension2)
                    .contains(new int[]{1, 2, 3}, atIndex(0))
                    .contains(new int[]{4, 5, 6}, atIndex(1))
                    .isDeepEqualTo(new int[][]{{1, 2, 3}, {4, 5, 6}});

            int[][] array3 = new int[2][3];
            int expectedFirstDimension3 = 2;
            int expectedSecondDimension3 = 3;

            assertThat(array3)
                    .hasDimensions(expectedFirstDimension3, expectedSecondDimension3)
                    .contains(new int[]{0, 0, 0}, atIndex(0))
                    .contains(new int[]{0, 0, 0}, atIndex(1))
                    .isDeepEqualTo(new int[][]{{0, 0, 0}, {0, 0, 0}});

            int[][] array4 = {};
            int expectedFirstDimension4 = 0;
            int expectedSecondDimension4 = 0;

            assertThat(array4)
                    .hasDimensions(expectedFirstDimension4, expectedSecondDimension4)
                    .isEmpty();
        }
    }

    @Nested
    @DisplayName("Shallow Copy vs. Deep Copy")
    class Describe_copy {

        @Test
        @DisplayName("[deep copy] Object.clone()")
        void sut_Object_clone() {
            int[] array1 = {1, 2, 3};
            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);

            int[] array2 = array1.clone();
            assertThat(array2)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);

            array2[1] = 0;
            assertThat(array2)
                    .hasSize(3)
                    .containsExactly(1, 0, 3);

            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("[deep copy] System.arraycopy()")
        void sut_System_arraycopy() {
            int[] array1 = {1, 2, 3};
            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);

            int[] array2 = new int[3];
            System.arraycopy(array1, 0, array2, 0, 2);
            assertThat(array2)
                    .hasSize(3)
                    .containsExactly(1, 2, 0);

            array2[1] = 0;
            assertThat(array2)
                    .hasSize(3)
                    .containsExactly(1, 0, 0);
            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("[deep copy] Arrays.copyOfRange()")
        void sut_Arrays_copyOfRange() {
            int[] array1 = {1, 2, 3};
            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);

            int[] array2 = Arrays.copyOfRange(array1, 0, 2);
            assertThat(array2)
                    .hasSize(2)
                    .containsExactly(1, 2);

            array2[1] = 0;
            assertThat(array2)
                    .hasSize(2)
                    .containsExactly(1, 0);

            assertThat(array1)
                    .hasSize(3)
                    .containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("[deep copy] 2차원 배열")
        void sut_System_arraycopy_two_dimensional_array() {
            int[][] array1 = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9},
            };

            int[][] array2 = new int[array1.length][array1[0].length];

            for (int i = 0; i < array2.length; i++) {
                System.arraycopy(array1[i], 0, array2[i], 0, array1[0].length);
            }

            assertThat(array2)
                    .isEqualTo(array1)
                    .isDeepEqualTo(array1);

            array2[1][1] = 0;
            assertThat(array2)
                    .isNotEqualTo(array1)
                    .isDeepEqualTo(new int[][]{
                            {1, 2, 3},
                            {4, 0, 6},
                            {7, 8, 9},
                    });
        }
    }

    @Nested
    @DisplayName("Arrays utility")
    class Describe_Arrays {

        @Test
        @DisplayName("sort")
        void sut_sort() {
            // Collections, Comparator를 사용하려면 객체 타입이어야 한다.
            Integer[] array1 = {1, 3, 2};

            Arrays.stream(array1).forEach(System.out::println);

            Arrays.sort(array1, Collections.reverseOrder());
            assertThat(array1).containsExactly(3, 2, 1);

            Arrays.sort(array1, Comparator.comparingInt(i -> i));
            assertThat(array1).containsExactly(1, 2, 3);

            Arrays.sort(array1, Comparator.reverseOrder());
            assertThat(array1).containsExactly(3, 2, 1);

            Arrays.sort(array1, new MyReverseComparator());
            assertThat(array1).containsExactly(3, 2, 1);

            Arrays.sort(array1, new MyNaturalComparator());
            assertThat(array1).containsExactly(1, 2, 3);

            List<Integer> list1 = Stream.of(array1).sorted().collect(Collectors.toList());
            assertThat(list1).containsExactly(1, 2, 3);

            List<Integer> list2 = Stream.of(array1).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            assertThat(list2).containsExactly(3, 2, 1);
        }

        class MyReverseComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                // 내림차순
                return o2.compareTo(o1);
            }
        }

        class MyNaturalComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                // 오름차순
                return o1.compareTo(o2);
            }
        }
    }
}
