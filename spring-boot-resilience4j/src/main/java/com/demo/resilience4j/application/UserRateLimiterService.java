package com.demo.resilience4j.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRateLimiterService {

    private final RedissonClient redissonClient;

    public RRateLimiter getUserRateLimiter(String userId) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("rateLimiter:" + userId);

        // 여기서 rateLimiter 설정을 초기화하거나 업데이트할 수 있습니다.
        // 예를 들어, 사용자별로 2초당 1개 요청으로 설정:

        /*
            Redis
            {
                "rate": 1,
                "interval": 2000, -> milliseconds
                "type": 0 -> RateType.OVERALL
            }
         */
        int rate = 1;
        int rateInterval = 2;
        rateLimiter.trySetRate(
        // rateLimiter.setRate( // 계속 업데이트하기 때문에 rate limit이 적용되지 않음
        //         RateType.PER_CLIENT,
                RateType.OVERALL,
                rate,
                rateInterval,
                RateIntervalUnit.SECONDS
        );

        return rateLimiter;
    }

}
