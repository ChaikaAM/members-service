package com.adviqo.members.exception;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalMemberDtoException.class)
    protected ResponseEntity<Object> handleIllegalMemberDtoException(IllegalMemberDtoException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ExceptionMessage(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = MemberNotFoundException.class)
    protected ResponseEntity<Object> handleMemberNotFoundException(MemberNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ExceptionMessage(ex), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Data
    private static class ExceptionMessage {
        private final String message;

        ExceptionMessage(Exception exception) {
            this.message = exception.getMessage();
        }
    }
}
