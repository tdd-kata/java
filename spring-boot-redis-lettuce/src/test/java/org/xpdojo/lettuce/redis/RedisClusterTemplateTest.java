package org.xpdojo.lettuce.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// @org.springframework.test.context.ActiveProfiles("cluster") // 해당 profile을 사용하도록 설정됨.
@org.junit.jupiter.api.condition.EnabledIfSystemProperty(named = "spring.profiles.active", matches = "cluster")
// 특정 profile일 때만 실행됨.
// @org.springframework.context.annotation.Profile("cluster") // SpringBootTest 에 적용안됨.
@SpringBootTest
class RedisClusterTemplateTest {

    private final String testKey = "test-key";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        redisTemplate.delete(testKey);
    }

    @AfterEach
    void tearDown() {
        redisTemplate.delete(testKey);
    }

    /**
     * <code>cluster-enabled yes</code>인 상태에서 제대로 연결되지 않으면
     * 다음과 같은 에러가 발생할 수 있다.
     *
     * <pre>
     * - cluster 연결: clusterdown hash slot not served.
     * </pre>
     *
     * <pre>
     * - slot 할당: RedisSystemException: Redis exception; nested exception is io.lettuce.core.cluster.PartitionSelectorException: Cannot determine a partition for slot 12539.
     * </pre>
     */
    @Test
    void should_set_string_key_string_value() {
        final String stringValue = "test-value";

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        valueOperations.set(testKey, stringValue, 3, TimeUnit.SECONDS);

        String expected = (String) valueOperations.get(testKey);
        assertThat(stringValue).isEqualTo(expected);
    }

}
