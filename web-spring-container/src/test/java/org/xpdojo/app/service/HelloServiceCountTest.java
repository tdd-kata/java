package org.xpdojo.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xpdojo.MySpringBootTest;
import org.xpdojo.app.repository.HelloRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 *     <code>@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)</code>
 *     <code>@Transactional</code>
 * </pre>
 */
@MySpringBootTest
class HelloServiceCountTest {

    @Autowired
    HelloService helloService;
    @Autowired
    HelloRepository helloRepository;

    @Test
    void increaseCount() {
        final String toby = "Toby";

        IntStream.rangeClosed(1, 3).forEach(count -> {
            helloService.sayHello(toby);
            assertThat(helloRepository.countOf(toby)).isEqualTo(count);
        });
     }

}
