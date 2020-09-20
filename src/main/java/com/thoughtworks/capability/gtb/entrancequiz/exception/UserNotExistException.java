package com.thoughtworks.capability.gtb.entrancequiz.exception;

public class UserNotExistException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public UserNotExistException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
