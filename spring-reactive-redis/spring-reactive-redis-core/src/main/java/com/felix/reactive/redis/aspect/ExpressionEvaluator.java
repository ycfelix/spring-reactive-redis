package com.felix.reactive.redis.aspect;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpressionEvaluator extends CachedExpressionEvaluator {


    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    public EvaluationContext createEvaluationContext(final Object object,
                                                     final Class<?> targetClass,
                                                     final Method method,
                                                     final Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        ExpressionRootObject root = new ExpressionRootObject(object, args);
        return new MethodBasedEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
    }

    public Object key(String conditionExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext) {
        return getExpression(this.conditionCache, elementKey, conditionExpression).getValue(evalContext);
    }

    private Method getTargetMethod(final Class<?> targetClass, final Method method) {
        final AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        return targetMethodCache.computeIfAbsent(methodKey, Key -> AopUtils.getMostSpecificMethod(method, targetClass));
    }


    @Data
    @RequiredArgsConstructor
    private static class ExpressionRootObject {

        private final Object object;

        private final Object[] args;

    }
}
