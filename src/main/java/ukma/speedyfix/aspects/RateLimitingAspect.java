package ukma.speedyfix.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ukma.speedyfix.exception.RateLimitExceededException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Profile("!test")
public class RateLimitingAspect {

    private final ConcurrentHashMap<String, AtomicInteger> userRequestCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Instant> userRequestTimestamps = new ConcurrentHashMap<>();
    private static final int REQUEST_LIMIT = 5;

    private final HttpServletRequest request;

    public RateLimitingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("execution(* ukma.speedyfix.controller.AuthController.authenticateUser(..))")
    public void rateLimit() {
        String userId = request.getRemoteAddr();
        Instant now = Instant.now();
        userRequestCounts.putIfAbsent(userId, new AtomicInteger(0));
        userRequestTimestamps.putIfAbsent(userId, now);

        Instant userStart = userRequestTimestamps.get(userId);
        AtomicInteger userCount = userRequestCounts.get(userId);

        if (ChronoUnit.MINUTES.between(userStart, now) >= 1) {
            userRequestCounts.put(userId, new AtomicInteger(1));
            userRequestTimestamps.put(userId, now);
        } else if (userCount.incrementAndGet() > REQUEST_LIMIT) {
            throw new RateLimitExceededException("Request limit exceeded for user " + userId);
        }
    }

    public ConcurrentMap<String, AtomicInteger> getUserRequestCounts() {
        return userRequestCounts;
    }

    public ConcurrentMap<String, Instant> getUserRequestTimestamps() {
        return userRequestTimestamps;
    }
}