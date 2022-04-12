package org.xpdojo.spring.testdouble.fixture.flight;

import java.time.LocalDate;

/**
 * xUnit Test Pattern
 */
public interface AuditLog {
    void logMessage(LocalDate date, String user, ActionCode actionCode, Object detail);
}
