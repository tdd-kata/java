package org.xpdojo.jdbc.repository.exception;

public class DuplicatePrimaryKeyException extends RuntimeException {

    public DuplicatePrimaryKeyException() {
    }

    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }

    public DuplicatePrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatePrimaryKeyException(Throwable cause) {
        super(cause);
    }

}
