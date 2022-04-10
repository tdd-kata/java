package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDate;

public interface AuditLog {
    void logMessage(LocalDate date, String user, ActionCode actionCode, Object detail);
}
