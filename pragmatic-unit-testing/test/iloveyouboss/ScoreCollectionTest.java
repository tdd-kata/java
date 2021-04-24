package iloveyouboss;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ScoreCollectionTest {

	@Test
	public void answerArithmeticMeanOfTwoNumbers() {
		// 준비
		var collection = new ScoreCollection();
		collection.add(() -> 5);
		collection.add(() -> 7);

		// 실행
		int actualResult = collection.arithmeticMean();
		
		// 단언
		assertThat(actualResult, equalTo(6));
	}

}
