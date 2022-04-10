package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDateTime;

/**
 * xUnit Test Pattern
 */
public interface TimeProvider {
    LocalDateTime getTime() throws TimeProviderException;
}
