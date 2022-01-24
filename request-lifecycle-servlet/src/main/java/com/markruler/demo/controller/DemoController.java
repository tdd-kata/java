package com.markruler.demo.controller;

import com.markruler.demo.fixtures.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class DemoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String home(HttpServletRequest request) {
        log.info("request remote host {}", request.getRemoteHost());
        return "hello";
    }

    @PostMapping(value = "/valid")
    public Hello valid(@RequestBody @Valid Hello hello) {
        log.info("hello {}", hello);
        /*
            curl --location --request POST 'http://localhost:8080/valid' \
            --header 'Content-Type: application/json' \
            --data-raw '{
                "username": "markruler"
            }'
         */
        return hello;
    }

    @GetMapping("/invalid")
    public void invalid() throws HttpClientErrorException {
        /*
            curl -X GET 'http://localhost:8080/invalid'
         */
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
