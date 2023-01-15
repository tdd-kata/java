package org.xpdojo.lettuce.redis;

import io.lettuce.core.ReadFrom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;

@Profile("cluster")
@Configuration
@EnableRedisRepositories
public class RedisClusterConfig {

    /**
     * Redis Cluster
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        // redis-cli cluster nodes
        RedisClusterConfiguration redisClusterConfiguration =
                new RedisClusterConfiguration()
                        .clusterNode("localhost", 7001)
                        .clusterNode("localhost", 7002)
                        .clusterNode("localhost", 7003);

        LettuceClientConfiguration clientConfiguration =
                LettuceClientConfiguration.builder()
                        // .readFrom(ReadFrom.REPLICA_PREFERRED)
                        .readFrom(ReadFrom.MASTER_PREFERRED) // master만 생성했을 때
                        .commandTimeout(Duration.of(5000, MILLIS))
                        .build();
        // LettucePoolingClientConfiguration.builder()
        //     .poolConfig(GenericObjectPoolConfig);

        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
    }

    /**
     * apache.commons-pool2이 필요하다.
     */
    // @Bean
    // public LettucePool lettucePool() {
    //     GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    //     poolConfig.setMasIdle(10);
    //     poolConfig.setMinIdle(8);
    //     return new DefaultLettucePool(host, port, poolConfig);
    // }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

}
