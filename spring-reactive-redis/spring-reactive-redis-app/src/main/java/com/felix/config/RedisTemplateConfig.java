package com.felix.config;

import com.felix.model.CustomModel;
import com.felix.reactive.redis.factory.ReactiveRedisTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@Configuration
public class RedisTemplateConfig {

    @Bean
    ReactiveRedisTemplate<String, CustomModel> redisTemplate(final ReactiveRedisTemplateFactory<String> stringRedisTemplateFactory) {
        return stringRedisTemplateFactory.getRedisTemplate(CustomModel.class);
    }
}
