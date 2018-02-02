package com.example.weatherapp.aspect;

import com.example.weatherapp.exception.RateLimitExceededException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Aspect
@Component
public class RateLimitAspect {

    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Around("@annotation(limit)")
    public Object rateLimit(ProceedingJoinPoint jp, RateLimit limit) throws Throwable {
        String key = createKey(jp, limit);
        RateLimiter limiter = limiters.computeIfAbsent(key, createLimiter(limit));
        if (limiter.tryAcquire()) {
            return jp.proceed();
        }
        throw new RateLimitExceededException();
    }

    private Function<String, RateLimiter> createLimiter(RateLimit limit) {
        return name -> RateLimiter.create(limit.value(), TimeUnit.MINUTES);
    }

    private String createKey(ProceedingJoinPoint jp, RateLimit limit) {
        return Optional.ofNullable(Strings.emptyToNull(limit.key())).orElseGet(() -> jp.toShortString());
    }
}
