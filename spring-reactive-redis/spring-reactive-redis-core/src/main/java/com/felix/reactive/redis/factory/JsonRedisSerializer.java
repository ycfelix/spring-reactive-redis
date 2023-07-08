package com.felix.reactive.redis.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;
@RequiredArgsConstructor
public class JsonRedisSerializer<T> implements RedisSerializer<T> {

    private final Class<T> type;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(T type) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        try {
            return objectMapper.readValue(bytes, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
