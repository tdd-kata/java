package com.demo.springbootopenfeign.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignGlobalConfig {

    /**
     * FeignClient 로그 레벨 설정.
     * Exception, Retryer 등의 로그 레벨도 여기서 설정된다.
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        // return Logger.Level.BASIC;
        return Logger.Level.FULL;
    }

}
