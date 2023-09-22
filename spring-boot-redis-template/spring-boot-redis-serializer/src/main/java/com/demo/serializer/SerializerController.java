package com.demo.serializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
public class SerializerController {

    private final Logger log = Logger.getLogger(SerializerController.class.getName());

    private final RedisTemplate<String, Object> template;

    @Autowired
    public SerializerController(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    @GetMapping("/se")
    public Object serializer() {
        ValueOperations<String, Object> valueOps = template.opsForValue();
        valueOps.set("test", new DemoDto("1", "mark", null), 60, TimeUnit.SECONDS);

        RedisSerializer<?> valueSerializer = template.getValueSerializer();
        log.info("serialize (1) >>> " + valueSerializer.getClass().getName());

        template.setValueSerializer(new Jackson2JsonRedisSerializer(DemoSubDto.class));

        RedisSerializer<?> newValueSerializer = template.getValueSerializer();
        final String newValueSerializerName = newValueSerializer.getClass().getName();
        log.info("serialize (2) >>> " + newValueSerializerName);

        return newValueSerializerName;
    }

}
