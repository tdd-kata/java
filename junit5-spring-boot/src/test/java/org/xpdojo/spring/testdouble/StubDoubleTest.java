package org.xpdojo.spring.testdouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.xpdojo.spring.testdouble.fixture.LocalDateTimeProviderStub;
import org.xpdojo.spring.testdouble.fixture.TimeDisplay;
import org.xpdojo.spring.testdouble.fixture.TimeProvider;
import org.xpdojo.spring.testdouble.fixture.TimeProviderException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StubDoubleTest {

    /**
     * 어떻게 하면 다른 소프트웨어 컴포넌트로부터의 간접 입력에 의존하는 로직을 독립적으로 검증할 수 있을까?
     * 실제 객체를 테스트용 객체로 교체해 테스트 대상 시스템(SUT)에 원하는 간접 입력을 보낸다.
     */
    @Nested
    @DisplayName("하드 코딩된 테스트 스텁")
    class HardCodedStub {

        @CsvSource({
                "0, 0, <span class=\"tinyBoldText\">Midnight</span>",
                "12, 0, <span class=\"tinyBoldText\">Noon</span>",
                "12, 1, <span class=\"tinyBoldText\">12:01 오후</span>",
        })
        @ParameterizedTest
        @DisplayName("Midnight test")
        void test_stub_midnight(int hour, int minute, String expected) {
            TimeDisplay timeDisplay = new TimeDisplay();
            // TimeProvider timeProvider = new LocalDateTimeProviderStub();
            LocalDateTimeProviderStub timeProvider = new LocalDateTimeProviderStub();
            timeProvider.setHour(hour);
            timeProvider.setMinute(minute);
            timeDisplay.setTimeProvider(timeProvider);

            final String actual = timeDisplay.getCurrentTimeAsHtmlFragment();

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("익명 내부 클래스로 만든 훼방꾼")
        void test_stub_saboteur() {
            TimeDisplay timeDisplay = new TimeDisplay();
            TimeProvider timeProvider = new TimeProvider() {
                @Override
                public LocalDateTime getTime() throws TimeProviderException {
                    throw new TimeProviderException();
                }
            };
            timeDisplay.setTimeProvider(timeProvider);

            final String actual = timeDisplay.getCurrentTimeAsHtmlFragment();

            assertThat(actual).isEqualTo("<span class=\"error\">Invalid Time</span>");
        }
    }
}
