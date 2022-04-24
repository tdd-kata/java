package com.markruler.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

// 순차 컬렉션
@DisplayName("List")
class ListTest {

    // Collection 인터페이스는 모두 제네릭이며, 요소 타입에 대응하는 타입 매개변수를 받는다.
    // 임의의 T 클래스를 요소로 저장하는 ArrayList<T>를 Generic이라고 하며, T를 타입 매개변수라고 한다.

    @Nested
    class Describe_ArrayList {

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

            assertThat(arrayList)
                    .hasSize(3)
                    .containsExactly(first, second, third);
        }

        @ParameterizedTest(name = "ArrayList({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("java.util을 사용한 List 선언")
        void sut_java_util(String first, String second, String third) {
            List<String> arrayList1 = List.of(first, second, third);
            assertThat(arrayList1)
                    .hasSize(3)
                    .containsExactly(first, second, third);

            List<String> arrayList2 = Arrays.asList(first, second, third);
            assertThat(arrayList2)
                    .hasSize(3)
                    .containsExactly(first, second, third);
        }

        /**
         * @see ArrayList#elementData
         * @see ArrayList#ensureCapacity(int)
         * @see ArrayList#grow(int)
         * @see ArrayList#newCapacity(int)
         */
        @Test
        void sut_ArrayList_capacity() {
            ArrayList<String> arrayList = new ArrayList<>();
            assertThat(arrayList).isEmpty();

            // int newCapacity = oldCapacity + (oldCapacity >> 1);
            arrayList.ensureCapacity(100);
            assertThat(arrayList).isEmpty();
        }

    }

    /**
     * thread-safe
     *
     * @see Collections#synchronizedList(List)
     */
    @Nested
    @DisplayName("Vector")
    class Describe_Vector {

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

            assertThat(vector)
                    .hasSize(3)
                    .containsExactly(first, second, third);
        }
    }

    @Nested
    @DisplayName("Stack")
    class Describe_Stack {

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

            assertThat(stack)
                    .hasSize(3)
                    .containsExactly(first, second, third);

            String peek1 = stack.peek();
            assertThat(peek1).isEqualTo(third);
            String peek2 = stack.peek();
            assertThat(peek2).isEqualTo(third);

            assertThat(stack)
                    .hasSize(3)
                    .first().isEqualTo(first);

            String pop1 = stack.pop();
            assertThat(pop1).isEqualTo(third);

            assertThat(stack)
                    .hasSize(2)
                    .last().isEqualTo(second);

            String pop2 = stack.pop();
            assertThat(pop2).isEqualTo(second);
        }
    }

    @Nested
    @DisplayName("LinkedList")
    class Describe_LinkedList {

        /**
         * @see LinkedList#add(int, Object)
         * @see LinkedList#linkLast(Object)
         * @see LinkedList#linkFirst(Object)
         */
        @ParameterizedTest(name = "LinkedList({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("LinkedList는 Deque와 같다")
        void sut_LinkedList(String first, String second, String third) {
            // deque:double-ended queue 양쪽에서 삽입
            LinkedList<String> linkedList = new LinkedList<>();
            linkedList.addFirst(first);
            linkedList.addLast(second);
            linkedList.add(third);

            assertThat(linkedList)
                    .hasSize(3)
                    .containsExactly(first, second, third);

            assertThat(linkedList.getFirst()).isEqualTo(first);
            assertThat(linkedList.get(1)).isEqualTo(second);
            assertThat(linkedList.getLast()).isEqualTo(third);

            String pollFirst = linkedList.pollFirst();
            assertThat(pollFirst).isEqualTo(first);

            assertThat(linkedList)
                    .hasSize(2);

            String pollLast = linkedList.pollLast();
            assertThat(pollLast).isEqualTo(third);

            assertThat(linkedList)
                    .hasSize(1);
        }

        /**
         * [수행속도]
         * <code>ArrayDeque</code>는 <code>Array</code>로 지원되며
         * <code>Array</code>는 <code>LinkedList</code>보다 cache-locality.
         * <code>LinkedList</code>는 다음 노드가 있는 곳으로 가려고 다른 간접적인 경로를 거쳐간다.
         * <p>
         * [메모리]
         * <code>ArrayDeque</code>는 다음 노드에 대한 추가 참조를 유지할 필요가 없으므로
         * <code>LinkedList</code>보다 메모리 효율적이다.
         *
         * @see ArrayDeque
         */
        @ParameterizedTest(name = "Deque({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("Deque")
        void sut_Deque(String first, String second, String third) {
            // deque:double-ended queue 양쪽에서 삽입
            Deque<String> deque = new LinkedList<>();
            deque.add(first);
            deque.addFirst(second);
            deque.addLast(third);

            assertThat(deque)
                    .hasSize(3)
                    .containsExactly(second, first, third);

            String peek1 = deque.peek();
            assertThat(peek1).isEqualTo(second);
            String peek2 = deque.peek();
            assertThat(peek2).isEqualTo(second);

            String poll1 = deque.poll();
            assertThat(poll1).isEqualTo(second);

            assertThat(deque).hasSize(2);

            String poll2 = deque.pollLast();
            assertThat(poll2).isEqualTo(third);

            assertThat(deque).hasSize(1);
        }

        /**
         * @see java.util.concurrent.ConcurrentLinkedQueue 메시지큐
         */
        @ParameterizedTest(name = "Queue({argumentsWithNames})")
        @CsvSource({
                "a, b, c",
                "1, 2, 3",
        })
        @DisplayName("Queue")
        void sut_Queue(String first, String second, String third) {
            // FIFO: First In First Out
            // tail에서만 요소를 삽입
            // Queue<String> linkedList = new LinkedList<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(first);
            queue.add(second);
            queue.add(third);

            assertThat(queue).hasSize(3);

            String peek1 = queue.peek();
            assertThat(peek1).isEqualTo(first);
            String peek2 = queue.peek();
            assertThat(peek2).isEqualTo(first);

            String poll1 = queue.poll();
            assertThat(poll1).isEqualTo(first);

            assertThat(queue).hasSize(2);

            String poll2 = queue.poll();
            assertThat(poll2).isEqualTo(second);

            assertThat(queue).hasSize(1);
        }
    }
}
