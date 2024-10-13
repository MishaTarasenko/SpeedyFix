package ukma.speedyfix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ukma.speedyfix.domain.StoInfo;

@Configuration
public class StoInfoConfiguration {

    @Bean("conditionalStoConfig")
    @ConditionalOnProperty(prefix = "sto", name = "weekend", havingValue = "Monday")
    @ConditionalOnExpression("'${sto.opening-hours}' > '10'")
    public StoInfo conditionalStoConfig() {
        StoInfo stoInfo = new StoInfo();
        stoInfo.setOpeningHours("8");
        stoInfo.setClosingHours("18");
        stoInfo.setWeekend("Sunday");
        return stoInfo;
    }

    @Bean("conditionalStoConfig")
    @ConditionalOnMissingBean(StoInfo.class)
    public StoInfo defaultStoConfig(@Value("${sto.opening-hours}") String openingHours,
                                    @Value("${sto.closing-hours}") String closingHours,
                                    @Value("${sto.weekend}") String weekend) {
        StoInfo stoInfo = new StoInfo();
        stoInfo.setOpeningHours(openingHours);
        stoInfo.setClosingHours(closingHours);
        stoInfo.setWeekend(weekend);
        return stoInfo;
    }
}
