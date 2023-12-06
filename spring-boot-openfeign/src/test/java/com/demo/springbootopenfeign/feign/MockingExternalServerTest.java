package com.demo.springbootopenfeign.feign;

import com.demo.springbootopenfeign.application.DemoService;
import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * wiremock을 사용한 FeignClient 대상 외부 서버(제어할 수 없는 의존성)를 Mocking해서 테스트.
 * <p>
 * NOTE: url을 properties로 전달함.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "api.callback.url=http://localhost:${wiremock.server.port}"
        }
)
// @AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
class MockingExternalServerTest {

    @Autowired
    private DemoService demoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class Describe_FeignClientMock {

        final DemoApiResponse demoApiResponse = new DemoApiResponse("Hello2 World");

        @BeforeEach
        void setUp() throws JsonProcessingException {
            WireMock.stubFor(
                    WireMock.post(WireMock.urlEqualTo("/hello2"))
                            .willReturn(
                                    aResponse()
                                            .withStatus(200)
                                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                            .withBody(objectMapper.writeValueAsString(demoApiResponse))
                            )
            );
        }

        @Test
        // @Tag("e2e")
        @DisplayName("외부 API 서버를 Mocking해서 테스트")
        void test_mocking_server_with_temp_message() {
            String message = demoService.hello2();
            assertThat(message).isEqualTo("Hello2 World");
        }
    }

}
