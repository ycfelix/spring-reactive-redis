package com.felix.reactive.redis.factory;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public abstract class ReactiveRedisTemplateFactory<K> {

    protected final ReactiveRedisConnectionFactory factory;

    protected final Map<Class<?>, ReactiveRedisTemplate<K, ?>> redisTemplateCache = new ConcurrentHashMap<>();

    public <V> ReactiveRedisTemplate<K, V> getRedisTemplate(Class<V> type) {
        if (redisTemplateCache.containsKey(type)) {
            return (ReactiveRedisTemplate<K, V>) redisTemplateCache.get(type);
        }
        final RedisSerializer<K> keySerializer = getKeySerializer(type);
        final RedisSerializer<V> valueSerializer = getValueSerializer(type);
        final RedisSerializationContext<K, V> builder = RedisSerializationContext.<K, V>newSerializationContext(keySerializer)
                .hashKey(keySerializer)
                .hashValue(valueSerializer)
                .value(valueSerializer)
                .build();
        final ReactiveRedisTemplate<K, V> template = new ReactiveRedisTemplate<>(factory, builder);
        redisTemplateCache.put(type, template);
        return template;
    }

    protected abstract <V> RedisSerializer<K> getKeySerializer(Class<V> type);

    protected <V> RedisSerializer<V> getValueSerializer(Class<V> type) {
        return new JsonRedisSerializer<>(type);
    }

}
