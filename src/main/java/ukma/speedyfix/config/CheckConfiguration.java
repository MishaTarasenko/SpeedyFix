package ukma.speedyfix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckConfiguration {

    @Bean("conditionalStoConfig")
    @ConditionalOnProperty(prefix = "sto", name = "weekend", havingValue = "Monday")
    @ConditionalOnExpression("'${sto.opening-hours}' > '10'")
    public StoConfig conditionalStoConfig() {
        StoConfig stoConfig = new StoConfig();
        stoConfig.setOpeningHours("8");
        stoConfig.setClosingHours("18");
        stoConfig.setWeekend("Sunday");
        return stoConfig;
    }

    @Bean("conditionalStoConfig")
    @ConditionalOnMissingBean(name = "conditionalStoConfig")
    public StoConfig defaultStoConfig(@Value("${sto.opening-hours}") String openingHours,
                                      @Value("${sto.closing-hours}") String closingHours,
                                      @Value("${sto.weekend}") String weekend) {
        StoConfig stoConfig = new StoConfig();
        stoConfig.setOpeningHours(openingHours);
        stoConfig.setClosingHours(closingHours);
        stoConfig.setWeekend(weekend);
        return stoConfig;
    }
}
