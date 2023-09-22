package com.demo.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private RedisRepository redisRepository;
	@Autowired
	private ObjectMapper mapper;

	final String testKey = "test";

	DemoSubDto child = new DemoSubDto("tom");
	DemoDto dto = new DemoDto("1", "mark", child);

	@Test
	void contextLoads() throws JsonProcessingException {
		redisRepository.set(testKey, dto, 1000);

		final Object actual = redisRepository.get(testKey);
		// Actual   :{"id"="1", "name"="test"}
		System.out.println(actual);

		// Expected :"{"id":"1","name":"test"}"
		assertThat(actual).isNotEqualTo(mapper.writeValueAsString(dto));
	}

	@Test
	void test_GenericJackson2JsonRedisSerializer() {
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

		byte[] result = serializer.serialize(dto);

		final String actual = new String(result);
		// {"@class":"com.demo.serializer.DemoDto","id":"1","name":"test"}
		System.out.println(actual);

		assertThat(actual).contains("@class");
	}

	@Test
	void test_Jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<DemoDto> serializer = new Jackson2JsonRedisSerializer<>(DemoDto.class);

		byte[] result = serializer.serialize(dto);

		final String actual = new String(result);
		// {"@class":"com.demo.springbootredisserializer.DemoDto","id":"1","name":"test"}
		System.out.println(actual);

		assertThat(actual).doesNotContain("@class");
	}

}
