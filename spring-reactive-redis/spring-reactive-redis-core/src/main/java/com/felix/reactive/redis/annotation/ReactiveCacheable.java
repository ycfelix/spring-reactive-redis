package com.felix.reactive.redis.annotation;

import com.felix.reactive.redis.serializer.JsonRedisSerializer;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReactiveCacheable {

    @AliasFor("value")
    String[] cacheNames() default {};

    @AliasFor("cacheNames")
    String[] value() default {};

    String key();

    Class<? extends RedisSerializer> serializer() default JsonRedisSerializer.class;
}
