package com.thoughtworks.capability.gtb.entrancequiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EducationException.class)
    public ResponseEntity EducationException(EducationException educationException) {
        ErrorResult errorResult = getErrorResult(HttpStatus.NOT_FOUND.value(),
                educationException.getExceptionEnum().getError(),
                educationException.getExceptionEnum().getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResult);
    }

    @ExceptionHandler(UserAddException.class)
    public ResponseEntity studentAddException(UserAddException userAddException) {
        ErrorResult errorResult = getErrorResult(HttpStatus.NOT_FOUND.value(),
                userAddException.getExceptionEnum().getError(),
                userAddException.getExceptionEnum().getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResult);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity studentNotExistException(UserNotExistException userNotExistException) {
        ErrorResult errorResult = getErrorResult(HttpStatus.NOT_FOUND.value(),
                userNotExistException.getExceptionEnum().getError(),
                userNotExistException.getExceptionEnum().getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResult);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintException(ConstraintViolationException ex) {
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handle(MethodArgumentNotValidException ex) {
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResult> requestParameterException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String message = parameterName + "是必填项";
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                message,
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResult> requestParameterNotMatch(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getParameter().getParameterName();
        String message = parameterName + "类型不匹配";
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                message,
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    private ErrorResult getErrorResult(Integer status, String error,
                                       String message, Instant date) {
        return ErrorResult.builder()
                .status(status).error(error)
                .timestamp(date).message(message).build();
    }
}
