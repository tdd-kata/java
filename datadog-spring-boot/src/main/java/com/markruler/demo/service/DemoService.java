package com.markruler.demo.service;

import com.markruler.demo.fixtures.Hello;

public interface DemoService {

    String home();

    Hello valid(Hello hello);

    Hello invalid();

    Hello error();
}
