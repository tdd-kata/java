package com.markruler.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class DemoControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(HttpClientErrorException ex) {
        // log.error(ex.toString(), ex);
        log.error(ex.toString());
        return ex.toString();
    }
}
