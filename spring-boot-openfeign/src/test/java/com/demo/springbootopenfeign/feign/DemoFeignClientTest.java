package com.demo.springbootopenfeign.feign;

import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.demo.springbootopenfeign.application.dto.DemoDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        // "feign.client.config.apiCallbackClient.connectTimeout=5000",
        // "feign.client.config.apiCallbackClient.readTimeout=5000",
        "feign.client.config.apiCallbackClient.loggerLevel=full",
})
@ActiveProfiles({"test"})
class DemoFeignClientTest {

    @Autowired
    private DemoFeignClient demoFeignClient;

    // @Disabled("외부 서버에 요청하는 FeignClient 테스트")
    @Tag("integration")
    @Test
    void test_feign_client() {
        // FeignClient headers가 null이면 아래 에러 발생
        // java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "object" is null
        Map<String, String> headers = Collections.emptyMap();

        DemoApiResponse callback =
                demoFeignClient.callback(
                        URI.create("http://localhost:38080/hello"), // test_server.py
                        headers,
                        new DemoDto("markruler", 20)
                );

        assertThat(callback.getMessage()).contains("hi markruler");
    }

}
