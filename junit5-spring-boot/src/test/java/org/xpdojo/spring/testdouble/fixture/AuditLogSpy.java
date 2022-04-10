package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDate;

public class AuditLogSpy implements AuditLog {

    // 실제 사용된 정보를 저장하는 데 쓰이는 필드들
    private LocalDate date;
    private String user;
    private ActionCode actionCode;
    private Object detail;
    private int numberOfCalls = 0;

    @Override
    public void logMessage(LocalDate date, String user, ActionCode actionCode, Object detail) {
        this.date = date;
        this.user = user;
        this.actionCode = actionCode;
        this.detail = detail;
        numberOfCalls++;
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
}
