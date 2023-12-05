package com.demo.springbootopenfeign.application;

import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.demo.springbootopenfeign.application.dto.DemoDto;
import com.demo.springbootopenfeign.feign.DemoFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoFeignClient demoFeignClient;

    public String hello() {
        URI uri = URI.create("http://localhost:38080/hello");

        // FeignClient headers가 null이면 아래 에러 발생
        // java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "object" is null
        Map<String, String> headers = Collections.emptyMap();

        DemoDto demoDto = new DemoDto("markruler", 20);

        DemoApiResponse callback =
                demoFeignClient.callback(
                        uri,
                        headers,
                        demoDto
                );

        log.debug("callback={}", callback);
        return callback.getMessage();
    }

}
