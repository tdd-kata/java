package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Spring Framework의 인터페이스 기반 변경은 대부분 전략 패턴이다")
class SpringTest {

    @Test
    void sut_application_context() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        assertThat(applicationContext)
                .isInstanceOf(ApplicationContext.class)
                .isInstanceOf(ClassPathXmlApplicationContext.class);

        applicationContext = new FileSystemXmlApplicationContext();
        assertThat(applicationContext)
                .isInstanceOf(ApplicationContext.class)
                .isInstanceOf(FileSystemXmlApplicationContext.class);

        applicationContext = new AnnotationConfigApplicationContext();
        assertThat(applicationContext)
                .isInstanceOf(ApplicationContext.class)
                .isInstanceOf(AnnotationConfigApplicationContext.class);
    }

    @Test
    @DisplayName("Spring Framework의 인터페이스 기반 변경은 대부분 전략 패턴")
    void sut_cache_manager() {
        CacheManager cacheManager = new NoOpCacheManager();
        assertThat(cacheManager)
                .isInstanceOf(CacheManager.class)
                .isInstanceOf(NoOpCacheManager.class);

        cacheManager = new SimpleCacheManager();
        assertThat(cacheManager)
                .isInstanceOf(CacheManager.class)
                .isInstanceOf(SimpleCacheManager.class);
    }
}
