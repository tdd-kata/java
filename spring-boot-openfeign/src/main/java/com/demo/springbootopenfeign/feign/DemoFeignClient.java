package com.demo.springbootopenfeign.feign;

import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.demo.springbootopenfeign.application.dto.DemoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.util.Map;


@FeignClient(
        name = "apiCallbackClient",
        url = "$", // java.net.URI을 인수로 받음
        configuration = SomeFeignConfig.class
)
public interface DemoFeignClient {

    @PostMapping
    DemoApiResponse callback(
            URI uri,
            @RequestHeader Map<String, String> headers,
            @RequestBody DemoDto body
    );

}
