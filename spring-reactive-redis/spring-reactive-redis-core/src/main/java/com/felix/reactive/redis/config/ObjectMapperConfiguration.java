package com.felix.reactive.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.reactive.redis.factory.DefaultJavaRedisTemplateFactory;
import com.felix.reactive.redis.factory.ReactiveRedisTemplateFactory;
import com.felix.reactive.redis.factory.StringRedisTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    ReactiveRedisTemplateFactory<String> defaultStringRedisTemplate(@Autowired final ReactiveRedisConnectionFactory connectionFactory) {
        return new StringRedisTemplateFactory(connectionFactory);
    }

    @Bean
    ReactiveRedisTemplateFactory<Object> defaultJavaRedisTemplate(@Autowired final ReactiveRedisConnectionFactory connectionFactory) {
        return new DefaultJavaRedisTemplateFactory(connectionFactory);
    }
}
