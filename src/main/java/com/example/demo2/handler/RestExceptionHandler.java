package com.example.demo2.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder().timestamp(new Date()).error(ex.getMessage()).status(HttpStatus.NOT_FOUND.value()).path(request.getDescription(false)).build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorDetails> handleEntityIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder().timestamp(new Date()).error(ex.getMessage()).status(HttpStatus.EXPECTATION_FAILED.value()).path(request.getDescription(false)).build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetails {
        private Date timestamp;
        private String error;
        private Integer status;
        private String path;
    }
}