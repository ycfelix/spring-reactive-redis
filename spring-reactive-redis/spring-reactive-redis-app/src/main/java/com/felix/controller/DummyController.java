package com.felix.controller;

import com.felix.model.CustomModel;
import com.felix.reactive.redis.annotation.ReactiveCacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class DummyController {

    private final ReactiveRedisTemplate<String, CustomModel> redisTemplate;

    @GetMapping
    @ReactiveCacheable(cacheNames = {}, value = "test", key = "")
    public Mono<Void> tryMe() {
        return Mono.empty();
    }

    @PostMapping("key")
    public Mono<CustomModel> set(@RequestBody CustomModel model) {
        return redisTemplate.opsForValue()
                .set(model.getRedisKey(), model)
                .thenReturn(model);
    }

    @GetMapping("key")
    public Mono<CustomModel> get(@RequestParam(value = "key") String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
