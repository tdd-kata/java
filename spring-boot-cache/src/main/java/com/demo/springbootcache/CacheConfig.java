package com.demo.springbootcache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    // @Bean("cacheManager")
    public CacheManager concurrentMapCacheManager() {
        return new ConcurrentMapCacheManager("cache::demo::data");
    }

    // @Bean("cacheManager")
    public CacheManager caffeineCacheManager() {
        final var cacheManager =
                new CaffeineCacheManager(
                        "cache::demo::data"
                );

        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .initialCapacity(200)
                        .maximumSize(500)
                        .weakKeys()
                        .recordStats()
        );
        return cacheManager;
    }

    /**
     * The bean 'cacheManager',
     * defined in class path resource [org/springframework/boot/autoconfigure/cache/RedisCacheConfiguration.class], could not be registered.
     * A bean with that name has already been defined in class path resource [com/demo/springbootcache/CacheConfig.class] and overriding is disabled.
     *
     *  @see <a href="https://www.baeldung.com/spring-boot-redis-cache">Spring Boot Cache with Redis</a>
     */
    // @Bean("cacheManager")
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration("cache::demo::data",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("cache::demo::data2",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

}
