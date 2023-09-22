package com.demo.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

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
	void test_getGeneric() throws JsonProcessingException {
		final Object actual = redisRepository.get(testKey);
		// Actual   :{"id"="1", "name"="test"}
		System.out.println(mapper.writeValueAsString(actual));

		// Expected :"{"id":"1","name":"test"}"
		assertThat(actual).isNotEqualTo(mapper.writeValueAsString(dto));
	}

	@Test
	void test_getWithClass() throws JsonProcessingException {
		final Object actual = redisRepository.getWithClass(testKey, DemoDto.class);
		// Actual   :{"id"="1", "name"="test"}
		System.out.println(mapper.writeValueAsString(actual));

		// Expected :"{"id":"1","name":"test"}"
		assertThat(actual).isNotEqualTo(mapper.writeValueAsString(dto));
	}

	@Test
	void test_GenericJackson2JsonRedisSerializer() {
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

		byte[] result = serializer.serialize(dto);

		final String actual = new String(result);
		// {"@class":"com.demo.deserializer.DemoDto","id":"1","name":"test"}
		System.out.println(actual);

		assertThat(actual).contains("@class");
	}

}
