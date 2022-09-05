package com.markruler.demo.controller;

import com.markruler.demo.fixtures.Hello;
import com.markruler.demo.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public String home(HttpServletRequest request) {
        log.info("request remote host {}", request.getRemoteHost());
        return demoService.home();
    }

    /**
     * <pre>
     *     curl --location --request POST 'http://localhost:8080/valid' \
     *          --header 'Content-Type: application/json' \
     *          --data-raw '{
     *              "username": "markruler"
     *          }'
     * </pre>
     *
     * @param hello
     * @return
     */
    @PostMapping(value = "/valid")
    public Hello valid(@RequestBody @Valid Hello hello) {
        log.info("hello {}", hello);
        return demoService.valid(hello);
    }

    /**
     * <pre>
     *     curl -X GET 'http://localhost:8080/invalid'
     * </pre>
     *
     * @throws HttpClientErrorException
     */
    @GetMapping("/invalid")
    public Hello invalid() throws HttpClientErrorException {
        return demoService.invalid();
    }

    /**
     * <pre>
     *     curl -X GET 'http://localhost:8080/error'
     * </pre>
     *
     * @throws HttpClientErrorException
     */
    @GetMapping("/error")
    public Hello error() throws HttpClientErrorException {
        return demoService.error();
    }
}
