package org.xpdojo.lettuce.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// @org.springframework.test.context.ActiveProfiles("standalone") // 해당 profile을 사용하도록 설정됨.
@org.junit.jupiter.api.condition.EnabledIfSystemProperty(named = "spring.profiles.active", matches = "standalone") // 특정 profile일 때만 실행됨.
// @org.springframework.context.annotation.Profile("standalone") // SpringBootTest 에 적용안됨.
@SpringBootTest
class RedisStandaloneTemplateTest {

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
     * <pre>
     *     127.0.0.1:6379> type "\xac\xed\x00\x05t\x00\btest-key"
     *     string
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

    /**
     * <pre>
     *     127.0.0.1:6379> type "\xac\xed\x00\x05t\x00\btest-key"
     *     hash
     * </pre>
     */
    @Test
    void should_set_string_key_map_value() {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

        final String key1 = "key1";
        final String value1 = "value1";
        final String key2 = "key2";
        final String value2 = "value2";

        hashOperations.put(testKey, key1, value1);
        hashOperations.put(testKey, key2, value2);

        Map<String, Object> mapValue = hashOperations.entries(testKey);
        assertThat(mapValue).hasSize(2).containsEntry(key1, value1).containsEntry(key2, value2);

        String expected1 = (String) hashOperations.get(testKey, key1);
        assertThat(expected1).isEqualTo(value1);

        String expected2 = (String) hashOperations.get(testKey, key2);
        assertThat(expected2).isEqualTo(value2);
    }

    /**
     * <pre>
     *     127.0.0.1:6379> type "\xac\xed\x00\x05t\x00\btest-key"
     *     list
     * </pre>
     */
    @Test
    void should_set_string_key_list_value() {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(testKey, "h");
        listOperations.rightPush(testKey, "e");
        listOperations.rightPush(testKey, "l");
        listOperations.rightPush(testKey, "l");
        listOperations.rightPush(testKey, "o");

        String character = (String) listOperations.index(testKey, 0);
        assertThat(character).isEqualTo("h");

        List<String> expected = Arrays.asList("h", "e", "l", "l", "o");

        Long size = listOperations.size(testKey);
        assertThat(size).isEqualTo(expected.size());

        List<Object> actual = listOperations.range(testKey, 0, -1);
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * <pre>
     *     type "\xac\xed\x00\x05t\x00\btest-key"
     *     set
     * </pre>
     */
    @Test
    void should_set_string_key_set_value() {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(testKey, "h");
        setOperations.add(testKey, "e");
        setOperations.add(testKey, "l");
        setOperations.add(testKey, "l");
        setOperations.add(testKey, "o");

        Long size = setOperations.size(testKey);
        assertThat(size).isEqualTo(4);

        // LinkedHashSet
        Set<Object> actual = setOperations.members(testKey);
        assertThat(actual).containsExactlyInAnyOrder("h", "e", "l", "o");

        Cursor<Object> cursor = setOperations.scan(testKey, ScanOptions.scanOptions().match("*").build());
        while (cursor.hasNext()) {
            assertThat(actual).contains(cursor.next());
        }
    }

    /**
     * <pre>
     *     type "\xac\xed\x00\x05t\x00\btest-key"
     *     zset
     * </pre>
     */
    @Test
    void should_set_string_key_zset_value() {
        ZSetOperations<String, Object> zsetOperations = redisTemplate.opsForZSet();
        zsetOperations.add(testKey, "h", 2);
        zsetOperations.add(testKey, "e", 4);
        zsetOperations.add(testKey, "l", 8);
        zsetOperations.add(testKey, "l", 16);
        zsetOperations.add(testKey, "o", 32);

        Long size = zsetOperations.size(testKey);
        assertThat(size).isEqualTo(4);

        // LinkedHashSet
        Set<Object> actual = zsetOperations.range(testKey, 0, -1);
        assertThat(actual).containsExactlyInAnyOrder("h", "e", "l", "o");

        Set<Object> actualByScore = zsetOperations.rangeByScore(testKey, 0, 8);
        assertThat(actualByScore).containsExactlyInAnyOrder("h", "e");

    }


}
