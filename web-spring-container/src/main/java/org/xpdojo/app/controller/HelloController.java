package org.xpdojo.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xpdojo.app.service.HelloService;

import java.util.Objects;

/**
 * Spring Container는
 * <code>@Controller</code>에 매핑 정보가 담겨 있을 거라고 추측하고
 * 매핑 정보를 찾아서 처리한다.
 */
@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(String name) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name is null or empty");
        }
        return helloService.sayHello(name);
    }

}
