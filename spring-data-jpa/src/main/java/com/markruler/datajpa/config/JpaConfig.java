package com.markruler.datajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

/**
 * @CreatedBy, @LastModifiedBy 을 저장하려 한다면
 * AuditorAware의 getCurrentAuditor 호출
 * @see com.markruler.datajpa.entity.AuditEntity
 */
@EnableJpaAuditing
// @SpringBootApplication를 사용하면 아래 어노테이션 생략 가능
// @EnableJpaRepositories(basePackages = "com.markruler.datajpa.repository")
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // @CreatedBy, @LastModifiedBy 주체
        // 아래는 예시로 UUID 랜덤값을 넘기는 것이고
        // 실무에서는 1. Spring Security Context에서 Principal의 정보를 가져오거나
        // 2. HttpSession에서 가져오는 등 현재 로그인 세션의 정보를 가져올 수 있다.
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
