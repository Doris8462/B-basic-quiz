package com.thoughtworks.capability.gtb.entrancequiz.exception;

public class EducationException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public EducationException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
