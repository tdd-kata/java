package com.markruler.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String home(HttpServletRequest request) {
        log.info("request remote host {}", request.getRemoteHost());
        return "hello";
    }

    @GetMapping("/invalid")
    public void invalid() throws HttpClientErrorException {
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
