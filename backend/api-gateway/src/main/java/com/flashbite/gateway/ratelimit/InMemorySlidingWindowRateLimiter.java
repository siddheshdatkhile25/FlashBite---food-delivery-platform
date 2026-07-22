package com.flashbite.gateway.ratelimit;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class InMemorySlidingWindowRateLimiter {
    private final ConcurrentMap<String, Deque<Long>> windows = new ConcurrentHashMap<>();
    private final Clock clock;

    public InMemorySlidingWindowRateLimiter() {
        this(Clock.systemUTC());
    }

    InMemorySlidingWindowRateLimiter(Clock clock) {
        this.clock = clock;
    }

    public RateLimitDecision evaluate(String key, int maxRequests, Duration window) {
        long now = clock.millis();
        long oldestAllowedTimestamp = now - window.toMillis();
        Deque<Long> requestTimestamps = windows.computeIfAbsent(key, ignored -> new ArrayDeque<>());

        synchronized (requestTimestamps) {
            while (!requestTimestamps.isEmpty() && requestTimestamps.peekFirst() <= oldestAllowedTimestamp) {
                requestTimestamps.removeFirst();
            }

            if (requestTimestamps.size() >= maxRequests) {
                long retryAfterMillis = requestTimestamps.peekFirst() + window.toMillis() - now;
                return RateLimitDecision.deny(Duration.ofMillis(Math.max(retryAfterMillis, 1L)));
            }

            requestTimestamps.addLast(now);
            return RateLimitDecision.allow();
        }
    }

    public record RateLimitDecision(boolean allowed, Duration retryAfter) {
        public static RateLimitDecision allow() {
            return new RateLimitDecision(true, Duration.ZERO);
        }

        public static RateLimitDecision deny(Duration retryAfter) {
            return new RateLimitDecision(false, retryAfter);
        }
    }
}
