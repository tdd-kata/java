package iloveyouboss.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ScoreCollectionTest {

    private ScoreCollection collection;

    @Before
    public void create() {
        collection = new ScoreCollection();
    }

    @Test
    public void answerArithmeticMeanOfTwoNumbers() {
        // Triple-A (AAA)
        // 준비 (Arrange): 테스트 코드를 실행하기 전에 시스템이 적절한 상태에 있는지 확인합니다.
        collection.add(() -> 5);
        collection.add(() -> 7);

        // 실행 (Act): 테스트 코드를 실행합니다.
        int actualResult = collection.arithmeticMean();

        // 단언 (Assert): 실행한 코드가 기대한 대로 동작하는지 확인합니다.
        assertThat(actualResult, equalTo(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenAddingNull() {
        collection.add(null);
    }

    @Test
    public void answersZeroWhenNoElementsAdded() {
        assertThat(collection.arithmeticMean(), equalTo(0));
    }

    @Test
    public void doesNotProperlyHandleIntegerOverflow() {
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 1);

        assertTrue(collection.arithmeticMean() < 0);
    }
}
