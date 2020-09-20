package com.thoughtworks.capability.gtb.entrancequiz.exception;

public class UserAddException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public UserAddException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
