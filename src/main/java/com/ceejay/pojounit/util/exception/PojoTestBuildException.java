package com.ceejay.pojounit.util.exception;

/**
 * RuntimeException that is thrown when building a {@link com.ceejay.pojounit.pojo.PojoTest} has failed
 */
public class PojoTestBuildException extends RuntimeException {
    public PojoTestBuildException(Throwable cause) {
        super(cause);
    }

    public PojoTestBuildException() {
    }

    public PojoTestBuildException(String message) {
        super(message);
    }

    public PojoTestBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
