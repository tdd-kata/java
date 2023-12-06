package com.demo.springbootopenfeign.feign;

import com.demo.springbootopenfeign.application.DemoService;
import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.demo.springbootopenfeign.application.dto.DemoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;

/**
 * FeignClient Bean Mocking 테스트
 */
@ExtendWith(MockitoExtension.class)
class MockingFeignClientTest {

    @Mock
    private DemoFeignClient demoFeignClient;

    @InjectMocks
    private DemoService demoService;

    @Nested
    class Describe_FeignClientMock {

        @BeforeEach
        void setUp() {
            given(demoFeignClient.callback(
                    any(URI.class),
                    anyMap(),
                    any(DemoDto.class))
            ).willReturn(new DemoApiResponse("Hello World"));
        }

        @Test
        @DisplayName("FeignClient 인터페이스를 Mocking해서 테스트")
        void test_mocking_server_with_temp_message() {
            String message = demoService.hello();
            assertThat(message).isEqualTo("Hello World");
        }
    }

}
