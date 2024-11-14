package ukma.speedyfix.service.info;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.StoInfo;
import ukma.speedyfix.domain.response.InfoResponse;

@Service
public class StoService {

    private final StoInfo stoInfo;

    public StoService(@Qualifier("conditionalStoConfig") StoInfo stoInfo) {
        this.stoInfo = stoInfo;
    }

    @Cacheable(cacheNames = "info")
    public InfoResponse getInfo() {
        System.out.println("Tatatatatata");
        return InfoResponse.builder()
                .startTime(stoInfo.getOpeningHours())
                .endTime(stoInfo.getClosingHours())
                .weekend(stoInfo.getWeekend())
                .build();
    }
}