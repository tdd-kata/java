package com.markruler.demo.controller;

import com.markruler.demo.fixtures.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class DemoControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(HttpClientErrorException ex) {
        log.error(ex.toString());
        return new ErrorResponse(ex.getLocalizedMessage());
    }

    @ExceptionHandler({HttpServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(HttpServerErrorException ex) {
        log.error(ex.toString());
        return new ErrorResponse(ex.getLocalizedMessage());
    }
}
