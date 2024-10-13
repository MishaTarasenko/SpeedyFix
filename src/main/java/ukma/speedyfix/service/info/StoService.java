package ukma.speedyfix.service.info;

import org.springframework.stereotype.Service;
import ukma.speedyfix.config.StoConfig;
import ukma.speedyfix.domain.response.InfoResponse;

@Service
public class StoService {

    private final StoConfig stoConfig;

    public StoService(StoConfig stoConfig) {
        this.stoConfig = stoConfig;
    }

    public InfoResponse getInfo() {
        return InfoResponse.builder()
                .startTime(stoConfig.getOpeningHours())
                .endTime(stoConfig.getClosingHours())
                .weekend(stoConfig.getWeekend())
                .build();
    }
}