package com.markruler.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Map")
class MapTest {

    @Nested
    @DisplayName("Hashtable")
    class Describe_Hashtable {

        @ParameterizedTest(name = "Hashtable({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
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

    }

    @Nested
    @DisplayName("HashMap")
    class Describe_HashMap {

        @ParameterizedTest(name = "HashMap({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
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
    }

    /**
     * TreeMap은 이진탐색트리(Binary Search Tree) 중에서도
     * 성능을 향상시킨 레드-블랙 트리(Red-Black Tree)로 구현되어 있다.
     * TreeSet과의 차이점은 TreeSet은 그냥 값만 저장한다면
     * TreeMap은 키와 값이 저장된 Map, Etnry를 저장한다는 점이다.
     *
     * @see <a href="https://coding-factory.tistory.com/557">TreeMap 예제</a>
     */
    @Nested
    @DisplayName("TreeMap")
    static class Describe_TreeMap {

        @ParameterizedTest(name = "TreeMap({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        void sut_TreeMap(String first, String second, String third) {
            TreeMap<String, Object> treeMap = new TreeMap<>();
            treeMap.put(first, "d");
            treeMap.put(second, "e");
            treeMap.put(third, "f");

            assertThat(treeMap)
                    .hasSize(3)
                    .containsEntry(first, "d")
                    .containsEntry(second, "e")
                    .containsEntry(third, "f")
                    .doesNotContainKey("invalid");

            assertThat(treeMap.firstKey()).isEqualTo(first);
            assertThat(treeMap.firstEntry()).isEqualTo(treeMap.entrySet().iterator().next());

            assertThat(treeMap.lastKey()).isEqualTo(third);
            assertThat(treeMap.lastEntry()).isEqualTo(Map.entry(third, "f"));
        }
    }

    @Nested
    @DisplayName("Properties")
    class Describe_Properties {

        @ParameterizedTest(name = "Properties({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
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
