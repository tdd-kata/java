package com.demo.springbootopenfeign.feign;


import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

// @Configuration 붙이면 전역 설정이 되어버리니 주의
public class SomeFeignConfig {

    /**
     * 최소 {period}초~최대 {maxPeriod}초의 간격으로 점점 증가하며, 최대 2번 재시도한다.
     * 모든 retry 시도가 실패하면 feign.RetryableException 발생
     */
    @Bean
    public Retryer.Default retryer() {
        long period = 3_000L;
        long maxPeriod = 5_000L;
        int maxAttempts = 5;
        return new Retryer.Default(
                period,
                maxPeriod,
                maxAttempts
        );
    }

    /**
     * Connection 타임아웃과 Read 타임아웃을 설정한다.
     */
    @Bean
    public Request.Options options() {
        long connectTimeout = 1_000L;
        // read timeout이 server의 응답시간(time.sleep(2))보다 짧으면 타임 아웃 발생
        long readTimeout = 1_000L;
        boolean followRedirects = true;
        return new Request.Options(
                connectTimeout,
                TimeUnit.MILLISECONDS,
                readTimeout,
                TimeUnit.MILLISECONDS,
                followRedirects
        );
    }

}
