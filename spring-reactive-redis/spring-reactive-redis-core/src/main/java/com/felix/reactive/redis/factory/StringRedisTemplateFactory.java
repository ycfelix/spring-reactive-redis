package com.felix.reactive.redis.factory;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

public class StringRedisTemplateFactory extends ReactiveRedisTemplateFactory<String> {

    //basic string key implementation
    public StringRedisTemplateFactory(final ReactiveRedisConnectionFactory factory) {
        super(factory);
    }

    @Override
    protected <V> RedisSerializer<String> getKeySerializer(Class<V> type) {
        return RedisSerializer.string();
    }
}
