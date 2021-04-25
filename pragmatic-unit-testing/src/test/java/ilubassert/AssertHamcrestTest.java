package ilubassert;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AssertHamcrestTest {

    @Test
    @Ignore("Expect to Fail")
    // @ExpectToFail
    public void assertDoublesEqual() {
        assertThat(2.32 * 3, equalTo(6.96));
    }


    @Test
    public void assertWithTolerance() {
        assertThat(Math.abs((2.32 * 3) - 6.96) < 0.0005, is(true));
    }

    @Test
    @Ignore("Expect to Fail")
    @ExpectToFail
    public void assertDoublesCloseTo() {
        // https://github.com/gilbutITbook/006814/blob/master/iloveyouboss_tdd-13/test/scratch/AssertTest.java#L184
        // WTF why do JUnit matches not include closeTo
        // assertThat(2.32 * 3, closeTo(6.96, 0.0005));
    }

}
