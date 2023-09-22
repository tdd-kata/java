package com.demo.serializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class DeserializerController {

    private final Logger log = Logger.getLogger(DeserializerController.class.getName());

    private final RedisTemplate<String, Object> template;

    @Autowired
    public DeserializerController(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    @GetMapping("/de")
    public Object deserializer() {
        ValueOperations<String, Object> valueOps = template.opsForValue();

        RedisSerializer<?> valueSerializer = template.getValueSerializer();
        log.info("deserialize >>> " + valueSerializer.getClass().getName());

        // template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return valueOps.get("test");
    }

}
