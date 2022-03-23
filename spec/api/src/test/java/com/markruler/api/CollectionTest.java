package com.markruler.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Java Collection Framework")
class CollectionTest {

    // Collection 인터페이스는 모두 제네릭이며, 요소 타입에 대응하는 타입 매개변수를 받는다.
    // 임의의 T 클래스를 요소로 저장하는 ArrayList<T>를 Generic이라고 하며, T를 타입 매개변수라고 한다.

    @Nested
    @DisplayName("List")
    class Describe_List {
        // 순차 컬렉션

        @ParameterizedTest(name = "ArrayList({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("ArrayList")
        void sut_ArrayList(String first, String second, String third) {
            List<String> arrayList = new ArrayList<>();
            arrayList.add(first);
            arrayList.add(second);
            arrayList.add(third);

            assertThat(arrayList).hasSize(3);
            assertThat(arrayList.get(0)).isEqualTo(first);
            assertThat(arrayList.get(1)).isEqualTo(second);
            assertThat(arrayList.get(2)).isEqualTo(third);
        }

        @ParameterizedTest(name = "Vector({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("Vector")
        void sut_Vector(String first, String second, String third) {
            List<String> vector = new Vector<>();
            vector.add(first);
            vector.add(second);
            vector.add(third);

            assertThat(vector).hasSize(3);
            assertThat(vector.get(0)).isEqualTo(first);
            assertThat(vector.get(1)).isEqualTo(second);
            assertThat(vector.get(vector.size() - 1)).isEqualTo(third);
        }

        @ParameterizedTest(name = "Stack({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        void sut_Stack(String first, String second, String third) {
            // Vector<String> stack = new Stack<>();
            Stack<String> stack = new Stack<>();
            stack.push(first);
            stack.push(second);
            stack.push(third);

            assertThat(stack).hasSize(3);
            assertThat(stack.get(0)).isEqualTo(first);
            assertThat(stack.get(1)).isEqualTo(second);
            assertThat(stack.get(stack.size() - 1)).isEqualTo(third);

            stack.peek();
            assertThat(stack)
                    .hasSize(3)
                    .first().isEqualTo(first);

            stack.pop();
            assertThat(stack)
                    .hasSize(2)
                    .last().isEqualTo(second);
        }

        @ParameterizedTest(name = "LinkedList({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("LinkedList")
        void sut_LinkedList(String first, String second, String third) {
            // tail에서만 요소를 삽입
            // Queue<String> linkedList = new LinkedList<>();
            // deque:double-ended queue 양쪽에서 삽입
            // Deque<String> linkedList = new LinkedList<>();
            LinkedList<String> linkedList = new LinkedList<>();
            linkedList.add(first);
            linkedList.add(second);
            linkedList.add(third);

            assertThat(linkedList).hasSize(3);
            assertThat(linkedList.getFirst()).isEqualTo(first);
            assertThat(linkedList.get(1)).isEqualTo(second);
            assertThat(linkedList.getLast()).isEqualTo(third);
        }
    }

    @Nested
    @DisplayName("Set")
    class Describe_Set {

        @ParameterizedTest(name = "HashSet({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("HashSet")
        void sut_HashSet(String first, String second, String third) {
            Set<String> hashSet = new HashSet<>();
            hashSet.add(first);
            hashSet.add(second);
            hashSet.add(third);

            assertThat(hashSet)
                    .hasSize(3)
                    .contains(first)
                    .contains(second)
                    .contains(third)
                    .doesNotContain("invalid");
        }

        @ParameterizedTest(name = "TreeSet({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("TreeSet")
        void sut_TreeSet(String first, String second, String third) {
            Set<String> treeSet = new TreeSet<>();
            treeSet.add(first);
            treeSet.add(second);
            treeSet.add(third);

            assertThat(treeSet)
                    .hasSize(3)
                    .contains(first)
                    .contains(second)
                    .contains(third)
                    .doesNotContain("invalid");
        }
    }

    @Nested
    @DisplayName("Map")
    class Describe_Map {

        @ParameterizedTest(name = "Hashtable({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("Hashtable")
        void sut_Hashtable(String first, String second, String third) {
            Map<String, Object> hashtable = new Hashtable<>();
            hashtable.put(first, "d");
            hashtable.put(second, "e");
            hashtable.put(third, "f");

            assertThat(hashtable)
                    .hasSize(3)
                    .containsEntry(first, "d")
                    .containsEntry(second, "e")
                    .containsEntry(third, "f")
                    .doesNotContainKey("invalid");
        }

        @ParameterizedTest(name = "HashMap({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("HashMap")
        void sut_HashMap(String first, String second, String third) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put(first, "d");
            hashMap.put(second, "e");
            hashMap.put(third, "f");

            assertThat(hashMap)
                    .hasSize(3)
                    .containsEntry(first, "d")
                    .containsEntry(second, "e")
                    .containsEntry(third, "f")
                    .doesNotContainKey("invalid");
        }

        @ParameterizedTest(name = "TreeMap({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("TreeMap")
        void sut_TreeMap(String first, String second, String third) {
            Map<String, Object> treeMap = new TreeMap<>();
            treeMap.put(first, "d");
            treeMap.put(second, "e");
            treeMap.put(third, "f");

            assertThat(treeMap)
                    .hasSize(3)
                    .containsEntry(first, "d")
                    .containsEntry(second, "e")
                    .containsEntry(third, "f")
                    .doesNotContainKey("invalid");
        }

        @ParameterizedTest(name = "Properties({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("Properties")
        void sut_Properties(String first, String second, String third) {
            Map<Object, Object> properties = new Properties();
            properties.put(first, "d");
            properties.put(second, "e");
            properties.put(third, "f");

            assertThat(properties)
                    .hasSize(3)
                    .containsEntry(first, "d")
                    .containsEntry(second, "e")
                    .containsEntry(third, "f")
                    .doesNotContainKey("invalid");
        }
    }
}
