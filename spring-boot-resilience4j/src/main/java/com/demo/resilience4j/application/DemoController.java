package com.demo.resilience4j.application;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final RateLimiter userApiRateLimiter;

    private final UserRateLimiterService userRateLimiterService;

    @GetMapping("/")
    public ResponseEntity<String> woAnnotation() {
        // by ChatGPT
        // RateLimiter를 사용하여 메소드 실행을 시도합니다.
        RateLimiter.waitForPermission(userApiRateLimiter);

        // 실제 비즈니스 로직 실행
        return ResponseEntity.ok(userRateLimiterService.hello());
    }

    @GetMapping("/hello")
    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(
            name = "simpleApiRateLimiter",
            fallbackMethod = "fallback"
    )
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok(userRateLimiterService.hello());
    }

    public ResponseEntity<String> fallback(RequestNotPermitted requestNotPermitted) {
        log.error("fallback method is called. reason: {}", requestNotPermitted.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Fallback Method: Too many requests");
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<String> handleRateLimitException(RequestNotPermitted exception) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Exception Handler: Too many requests");
    }

}
