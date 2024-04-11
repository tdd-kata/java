package com.demo.resilience4j.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jRateLimiterConfig {

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        // RateLimiter의 전역 설정을 정의합니다.
        RateLimiterConfig config =
                RateLimiterConfig.custom()
                        // .limitForPeriod(10) // 기간 동안 허용되는 호출 수
                        .limitForPeriod(1) // 기간 동안 허용되는 호출 수
                        .limitRefreshPeriod(Duration.ofSeconds(1)) // 제한이 새로 고침되는 기간
                        .timeoutDuration(Duration.ofSeconds(0)) // 요청이 대기하는 시간
                        .build();

        return RateLimiterRegistry.of(config);
    }

    @Bean
    public RateLimiterRegistry userLimiterRegistry() {
        // RateLimiter의 전역 설정을 정의합니다.
        RateLimiterConfig config =
                RateLimiterConfig.custom()
                        .limitForPeriod(1) // 기간 동안 허용되는 호출 수
                        .limitRefreshPeriod(Duration.ofSeconds(1)) // 제한이 새로 고침되는 기간
                        .timeoutDuration(Duration.ofSeconds(0)) // 요청이 대기하는 시간
                        .build();

        return RateLimiterRegistry.of(config);
    }

    @Bean
    public RateLimiter simpleApiRateLimiter(RateLimiterRegistry rateLimiterRegistry) {
        return rateLimiterRegistry.rateLimiter("simpleApiRateLimiter");
    }

    @Bean
    public RateLimiter userApiRateLimiter(RateLimiterRegistry userLimiterRegistry) {
        return userLimiterRegistry.rateLimiter("userApiRateLimiter");
    }

}
