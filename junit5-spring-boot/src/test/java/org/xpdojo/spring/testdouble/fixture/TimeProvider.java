package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime getTime() throws TimeProviderException;
}
