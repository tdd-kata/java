package com.demo.serializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * <pre>
     *     redis-cli type test
     *     > string
     *     redis-cli get test
     *     > "{\"id\":\"1\",\"name\":\"test\"}"
     * </pre>
     */
    public void set(String key, Object o, long second) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, o, second, TimeUnit.SECONDS);
    }

    // @Autowired
    // private ObjectMapper objectMapper;

    public Object get(String key) {
        // redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        // redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return valueOps.get(key);
    }

}
