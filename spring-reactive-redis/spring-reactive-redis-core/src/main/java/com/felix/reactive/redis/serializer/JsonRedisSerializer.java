package com.felix.reactive.redis.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonRedisSerializer<T> implements RedisSerializer<T> {
    private final ObjectMapper objectMapper; //allow override objectMapper

    @Override
    public byte[] serialize(T t) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Fail serializing to json object");
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return objectMapper.convertValue(bytes, new TypeReference<T>() {});
    }
}
