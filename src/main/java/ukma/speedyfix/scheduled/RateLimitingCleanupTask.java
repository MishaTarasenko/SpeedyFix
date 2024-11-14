package ukma.speedyfix.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ukma.speedyfix.aspects.RateLimitingAspect;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class RateLimitingCleanupTask {

    private final RateLimitingAspect rateLimitingAspect;

    @Autowired
    public RateLimitingCleanupTask(RateLimitingAspect rateLimitingAspect) {
        this.rateLimitingAspect = rateLimitingAspect;
    }

    @Scheduled(fixedRate = 60000)
    public void cleanup() {
        Instant now = Instant.now();
        rateLimitingAspect.getUserRequestTimestamps().forEach((userId, timestamp) -> {
            if (ChronoUnit.MINUTES.between(timestamp, now) >= 1) {
                rateLimitingAspect.getUserRequestCounts().remove(userId);
                rateLimitingAspect.getUserRequestTimestamps().remove(userId);
            }
        });
    }
}
