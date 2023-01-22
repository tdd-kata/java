package org.xpdojo.webspringcontainer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 서버를 미리 실행시켜야 한다.
 */
class HelloApiTest {

    @Test
    void helloApi() {
        // http localhost:8080/hello?name=Spring
        TestRestTemplate rest = new TestRestTemplate();
        ResponseEntity<String> response = rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        // status code 200
        // header(content-type) text/plain
        // body Hello Spring
        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
                .startsWith(MediaType.TEXT_PLAIN_VALUE);
        Assertions.assertThat(response.getBody())
                .isEqualTo("Hello Spring!");
    }

    @Test
    void failsHelloApi() {
        // http localhost:8080/hello
        TestRestTemplate rest = new TestRestTemplate();
        ResponseEntity<String> response = rest.getForEntity("http://localhost:8080/hello", String.class);

        // status code 200
        // header(content-type) text/plain
        // body Hello Spring
        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
