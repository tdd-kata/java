package com.demo.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private RedisTemplate<String, Object> template;
    @Autowired
    private ObjectMapper mapper;

    final String testKey = "test";

    DemoSubDto child = new DemoSubDto("tom");
    DemoDto dto = new DemoDto("1", "mark", child);

    @DisplayName("RedisTemplate의 ValueSerializer를 변경하면 전역 변경")
    @Test
    void test_change_serializer() throws JsonProcessingException {
        RedisSerializer<?> valueSerializer = template.getValueSerializer();
        System.out.println(valueSerializer.getClass());
        // class org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

        // change
        template.setValueSerializer(new Jackson2JsonRedisSerializer(DemoDto.class));

        RedisSerializer<?> newValueSerializer = template.getValueSerializer();
        System.out.println(newValueSerializer.getClass());
        // class org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer

        assertThat(newValueSerializer.canSerialize(DemoDto.class)).isTrue();
    }

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

    @DisplayName("Generic으로 직렬화 후 Type 지정해서 역직렬화")
    @Nested
    class Describe_Generic2Specific {

        @DisplayName("@class 필드가 무엇인지 몰라서 에러 발생")
        @Test
        void test_generic_serialize_specific_deserialize1() {
            GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer();
            Object serialize = redisRepository.get(testKey);
            byte[] saved = genericSerializer.serialize(serialize);

            /*
            org.springframework.data.redis.serializer.SerializationException: Could not read JSON: Unrecognized field "@class" (class com.demo.deserializer.DemoDto), not marked as ignorable (3 known properties: "id", "children", "name"])
             at [Source: (byte[])"{"@class":"java.util.LinkedHashMap","id":"1","name":"mark","children":{"@class":"java.util.LinkedHashMap","name":"tom"}}"; line: 1, column: 12] (through reference chain: com.demo.deserializer.DemoDto["@class"]); nested exception is com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "@class" (class com.demo.deserializer.DemoDto), not marked as ignorable (3 known properties: "id", "children", "name"])
             at [Source: (byte[])"{"@class":"java.util.LinkedHashMap","id":"1","name":"mark","children":{"@class":"java.util.LinkedHashMap","name":"tom"}}"; line: 1, column: 12] (through reference chain: com.demo.deserializer.DemoDto["@class"])
                at org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer.deserialize(Jackson2JsonRedisSerializer.java:75)
             */
            Jackson2JsonRedisSerializer specificSerializer = new Jackson2JsonRedisSerializer(DemoDto.class);
            Object deserialize = specificSerializer.deserialize(saved);
            System.out.println(deserialize);
        }

        @DisplayName("에러가 발생하진 않지만 @class 필드 있음")
        @Test
        void test_generic_serialize_specific_deserialize2() {
            GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer();
            Object serialize = redisRepository.get(testKey);
            byte[] saved = genericSerializer.serialize(serialize);
            System.out.println(new String(saved));

            /*
                {"@class":"java.util.LinkedHashMap","id":"1","name":"mark","children":{"@class":"java.util.LinkedHashMap","name":"tom"}}
                {@class=java.util.LinkedHashMap, id=1, name=mark, children={@class=java.util.LinkedHashMap, name=tom}}
             */
            Jackson2JsonRedisSerializer specificSerializer = new Jackson2JsonRedisSerializer(Map.class);
            Object deserialize = specificSerializer.deserialize(saved);
            System.out.println(deserialize);

            // assertThat(actual).contains("@class");
        }

        @DisplayName("에러 발생")
        @Test
        void test_generic_serialize_specific_deserialize3() {
            GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer();
            Object serialize = redisRepository.get(testKey);
            byte[] saved = genericSerializer.serialize(serialize);
            System.out.println(new String(saved));

            /*
                org.springframework.data.redis.serializer.SerializationException: Could not read JSON: Cannot deserialize value of type `java.lang.String` from Object value (token `JsonToken.START_OBJECT`)
                 at [Source: (byte[])"{"@class":"java.util.LinkedHashMap","id":"1","name":"mark","children":{"@class":"java.util.LinkedHashMap","name":"tom"}}"; line: 1, column: 1]; nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.String` from Object value (token `JsonToken.START_OBJECT`)
                 at [Source: (byte[])"{"@class":"java.util.LinkedHashMap","id":"1","name":"mark","children":{"@class":"java.util.LinkedHashMap","name":"tom"}}"; line: 1, column: 1]
             */
            Jackson2JsonRedisSerializer specificSerializer = new Jackson2JsonRedisSerializer(String.class);
            Object deserialize = specificSerializer.deserialize(saved);
            System.out.println(deserialize);

            // final String actual = new String(result);
            // {"@class":"com.demo.deserializer.DemoDto","id":"1","name":"test"}

            // assertThat(actual).contains("@class");
        }

    }

}
