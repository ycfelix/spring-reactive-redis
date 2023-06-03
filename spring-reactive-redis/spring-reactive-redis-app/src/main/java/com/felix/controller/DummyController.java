package com.felix.controller;

import com.felix.reactive.redis.annotation.ReactiveCacheable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("test")
public class DummyController {

    @GetMapping
    @ReactiveCacheable(cacheNames = {}, value = "test", key = "")
    public Mono<Void> tryMe() {
        return Mono.empty();
    }
}
