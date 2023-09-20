package com.example.rinhajava.pessoa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
class RedisConfig {

    @Bean
    public RedisTemplate<String, PessoaDto> redisTemplateConfig(RedisConnectionFactory factory) {

        RedisTemplate<String, PessoaDto> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<PessoaDto> jsonRedisSerializer = new Jackson2JsonRedisSerializer<PessoaDto>(PessoaDto.class);
        template.setValueSerializer(jsonRedisSerializer);

        return template;

    }

}
