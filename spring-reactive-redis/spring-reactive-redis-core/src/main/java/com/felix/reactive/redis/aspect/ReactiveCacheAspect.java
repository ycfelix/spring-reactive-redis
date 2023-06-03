package com.felix.reactive.redis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ReactiveCacheAspect {

    @Pointcut("@annotation(com.felix.reactive.redis.annotation.ReactiveCacheable)")
    public void cacheablePointCut() {

    }

    @Around("cacheablePointCut()")
    public Object aroundCacheable(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("attempting to do something");
        return proceedingJoinPoint.proceed();
    }
}
