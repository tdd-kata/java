package org.xpdojo.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xpdojo.MySpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * <pre>
 *     <code>@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)</code>
 *     <code>@Transactional</code>
 * </pre>
 */
@MySpringBootTest
// @Rollback(false)
class HelloRepositoryTest {

    @Autowired
    HelloRepository helloRepository;

    private final String toby = "Toby";

    @Test
    void findHelloFailed() {
        assertThat(helloRepository.findHello(toby)).isNull();
    }

    @Test
    void increaseCount() {
        helloRepository.increaseCount(toby);
        assertThat(helloRepository.countOf(toby)).isEqualTo(1);

        helloRepository.increaseCount(toby);
        assertThat(helloRepository.countOf(toby)).isEqualTo(2);

        helloRepository.increaseCount(toby);
        assertThat(helloRepository.countOf(toby)).isEqualTo(3);
    }
}
