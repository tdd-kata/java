package com.markruler.demo.service;

import com.markruler.demo.fixtures.Hello;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String home() {
        return "hello";
    }

    @Override
    public Hello valid(Hello hello) {
        return hello;
    }

    @Override
    public Hello invalid() {
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Hello error() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
