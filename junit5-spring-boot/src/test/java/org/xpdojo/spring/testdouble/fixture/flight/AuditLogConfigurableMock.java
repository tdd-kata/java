package org.xpdojo.spring.testdouble.fixture.flight;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * xUnit Test Pattern
 */
public class AuditLogConfigurableMock implements AuditLog {

    // 실제 사용된 정보를 저장하는 데 쓰이는 필드들
    private LocalDate date;
    private String user;
    private ActionCode actionCode;
    private Object detail;
    private int numberOfCalls = 0;
    private int expectedNumberOfCalls = 0;

    public void setExpectedLogMessage(LocalDate date, String user, ActionCode actionCode, Object detail) {
        this.date = date;
        this.user = user;
        this.actionCode = actionCode;
        this.detail = detail;
    }

    public void setExpectedNumberOfCalls(int number) {
        this.expectedNumberOfCalls = number;
    }

    @Override
    public void logMessage(LocalDate date, String user, ActionCode actionCode, Object detail) {
        numberOfCalls++;
        assertThat(date).isEqualTo(this.date);
        assertThat(user).isEqualTo(this.user);
        assertThat(actionCode).isEqualTo(this.actionCode);
        assertThat(detail).isEqualTo(this.detail);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public ActionCode getActionCode() {
        return actionCode;
    }

    public Object getDetail() {
        return detail;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void verify() {
        assertThat(numberOfCalls).isEqualTo(expectedNumberOfCalls);
    }
}
