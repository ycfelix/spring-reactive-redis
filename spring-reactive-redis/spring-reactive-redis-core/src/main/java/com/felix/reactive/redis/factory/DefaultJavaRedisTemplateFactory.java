package com.felix.reactive.redis.factory;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

public class DefaultJavaRedisTemplateFactory extends ReactiveRedisTemplateFactory<Object> {

    //basic string key implementation
    public DefaultJavaRedisTemplateFactory(final ReactiveRedisConnectionFactory factory) {
        super(factory);
    }

    @Override
    protected <V> RedisSerializer<Object> getKeySerializer(Class<V> type) {
        return RedisSerializer.java(type.getClassLoader());
    }
}
