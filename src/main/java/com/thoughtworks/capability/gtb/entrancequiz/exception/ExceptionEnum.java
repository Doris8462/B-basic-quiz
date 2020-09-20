package com.thoughtworks.capability.gtb.entrancequiz.exception;

public enum  ExceptionEnum {

    USER_NOT_EXIST("NOT FOUND","user is not exist"),
    ADD_USER_EXCEPTION("SERVER ERROR", "add user failed"),
    REQUEST_PARAMETER_NOT_MATCH("CLIENT ERROR", "the parameters are not match the rule"),
    ADD_EDUCATION_EXCEPTION("SERVER ERROR", "add education failed");

    private String error;
    private String message;


    ExceptionEnum(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
