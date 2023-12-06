package com.demo.springbootopenfeign.feign;

import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.demo.springbootopenfeign.application.dto.DemoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 실제 외부 서버에 요청하는 FeignClient 테스트
 */
@SpringBootTest(properties = {
        // "feign.client.config.apiCallbackClient.connectTimeout=5000",
        // "feign.client.config.apiCallbackClient.readTimeout=5000",
        "feign.client.config.apiCallbackClient.loggerLevel=full",
})
@ActiveProfiles({"test"})
class ClassicDemoFeignClientTest {

    @Autowired
    private DemoFeignClient demoFeignClient;

    @Test
    @Tag("integration")
    @DisplayName("FeignClient를 Mocking해서 실제 외부 서버에 요청하는 테스트")
    // @Disabled
    void test_feign_client_with_real_server() {
        // FeignClient headers가 null이면 아래 에러 발생
        // java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "object" is null
        Map<String, String> headers = Collections.emptyMap();

        // Feign Client 부분을 Mocking
        DemoApiResponse callback =
                demoFeignClient.callback(
                        URI.create("http://localhost:38080/hello"), // test_server.py
                        headers,
                        new DemoDto("markruler", 20)
                );

        assertThat(callback.getMessage()).contains("hi markruler");
    }

}
