package com.demo.springbootopenfeign.feign;

import com.demo.springbootopenfeign.application.dto.DemoApiResponse;
import com.demo.springbootopenfeign.application.dto.DemoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "urlCallbackClient",
        url = "${api.callback.url}",
        configuration = SomeFeignConfig.class
)
public interface UrlFeignClient {

    @PostMapping("/hello2")
    DemoApiResponse callback(
            @RequestBody DemoDto body
    );

}
