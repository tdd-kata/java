package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * '실제 시간'에 의존하는 컴포넌트의 기능은 테스트를 거의 통과하지 못한다.
 * 여기서는 SUT의 간접 입력을 제어하는 응답기를 써서 조건을 테스트한다.
 */
public class LocalDateTimeProviderStub implements TimeProvider {

    private int hour;
    private int minute;

    @Override
    public LocalDateTime getTime() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(hour, minute);
        return LocalDateTime.of(date, time);
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
