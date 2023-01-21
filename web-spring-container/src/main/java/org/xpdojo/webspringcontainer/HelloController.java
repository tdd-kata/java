package org.xpdojo.webspringcontainer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
